package com.wms.newwmsapp.volley.toolbox;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import java.util.HashMap;
import java.util.LinkedList;
import com.wms.newwmsapp.volley.Request;
import com.wms.newwmsapp.volley.RequestQueue;
import com.wms.newwmsapp.volley.VolleyError;
import com.wms.newwmsapp.volley.Response.ErrorListener;
import com.wms.newwmsapp.volley.Response.Listener;
import com.wms.newwmsapp.volley.toolbox.ImageRequest;

public class ImageLoader {
	/** RequestQueue for dispatching ImageRequests onto. */
	private final RequestQueue mRequestQueue;

	/**
	 * Amount of time to wait after first response arrives before delivering all
	 * responses.
	 */
	private int mBatchResponseDelayMs = 100;

	/**
	 * The cache implementation to be used as an L1 cache before calling into
	 * volley.
	 */
	private final ImageCache mCache;

	/**
	 * HashMap of Cache keys -> BatchedImageRequest used to track in-flight
	 * requests so that we can coalesce multiple requests to the same URL into a
	 * single network request.
	 */
	private final HashMap<String, BatchedImageRequest> mInFlightRequests = new HashMap<String, BatchedImageRequest>();

	/** HashMap of the currently pending responses (waiting to be delivered). */
	private final HashMap<String, BatchedImageRequest> mBatchedResponses = new HashMap<String, BatchedImageRequest>();

	/** Handler to the main thread. */
	private final Handler mHandler = new Handler(Looper.getMainLooper());

	/** Runnable for in-flight response delivery. */
	private Runnable mRunnable;

	/**
	 * Simple cache adapter interface. If provided to the ImageLoader, it will
	 * be used as an L1 cache before dispatch to Volley. Implementations must
	 * not block. Implementation with an LruCache is recommended.
	 */
	public interface ImageCache {
		public Bitmap getBitmap(String url);

		public void putBitmap(String url, Bitmap bitmap);
	}

	/**
	 * Constructs a new ImageLoader.
	 * 
	 * @param queue
	 *            The RequestQueue to use for making image requests.
	 * @param imageCache
	 *            The cache to use as an L1 cache.
	 */
	public ImageLoader(RequestQueue queue, ImageCache imageCache) {
		mRequestQueue = queue;
		mCache = imageCache;
	}

	/**
	 * The default implementation of ImageListener which handles basic
	 * functionality of showing a default image until the network response is
	 * received, at which point it will switch to either the actual image or the
	 * error image.
	 * 
	 * @param view
	 *            The imageView that the listener is associated with.
	 * @param defaultImageResId
	 *            Default image resource ID to use, or 0 if it doesn't exist.
	 * @param errorImageResId
	 *            Error image resource ID to use, or 0 if it doesn't exist.
	 */
	public static ImageListener getImageListener(final ImageView view,
			final int defaultImageResId, final int errorImageResId) {
		return new ImageListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (errorImageResId != 0) {
					view.setImageResource(errorImageResId);
				}
			}

			@Override
			public void onResponse(ImageContainer response, boolean isImmediate) {
				if (response.getBitmap() != null) {
					view.setImageBitmap(response.getBitmap());
				} else if (defaultImageResId != 0) {
					view.setImageResource(defaultImageResId);
				}
			}
		};
	}

	public interface ImageListener extends ErrorListener {
		public void onResponse(ImageContainer response, boolean isImmediate);
	}

	public boolean isCached(String requestUrl, int maxWidth, int maxHeight) {
		return isCached(requestUrl, maxWidth, maxHeight,
				ScaleType.CENTER_INSIDE);
	}

	public boolean isCached(String requestUrl, int maxWidth, int maxHeight,
			ScaleType scaleType) {
		throwIfNotOnMainThread();

		String cacheKey = getCacheKey(requestUrl, maxWidth, maxHeight,
				scaleType);
		return mCache.getBitmap(cacheKey) != null;
	}

	public ImageContainer get(String requestUrl, final ImageListener listener) {
		return get(requestUrl, listener, 0, 0);
	}

	public ImageContainer get(String requestUrl, ImageListener imageListener,
			int maxWidth, int maxHeight) {
		return get(requestUrl, imageListener, maxWidth, maxHeight,
				ScaleType.CENTER_INSIDE);
	}

	public ImageContainer get(String requestUrl, ImageListener imageListener,
			int maxWidth, int maxHeight, ScaleType scaleType) {

		throwIfNotOnMainThread();

		final String cacheKey = getCacheKey(requestUrl, maxWidth, maxHeight,
				scaleType);

		Bitmap cachedBitmap = mCache.getBitmap(cacheKey);
		if (cachedBitmap != null) {
			ImageContainer container = new ImageContainer(cachedBitmap,
					requestUrl, null, null);
			imageListener.onResponse(container, true);
			return container;
		}

		ImageContainer imageContainer = new ImageContainer(null, requestUrl,
				cacheKey, imageListener);

		imageListener.onResponse(imageContainer, true);

		BatchedImageRequest request = mInFlightRequests.get(cacheKey);
		if (request != null) {
			request.addContainer(imageContainer);
			return imageContainer;
		}

		Request<Bitmap> newRequest = makeImageRequest(requestUrl, maxWidth,
				maxHeight, scaleType, cacheKey);

		mRequestQueue.add(newRequest);
		mInFlightRequests.put(cacheKey, new BatchedImageRequest(newRequest,
				imageContainer));
		return imageContainer;
	}

	protected Request<Bitmap> makeImageRequest(String requestUrl, int maxWidth,
			int maxHeight, ScaleType scaleType, final String cacheKey) {
		return new ImageRequest(requestUrl, new Listener<Bitmap>() {
			@Override
			public void onResponse(Bitmap response) {
				onGetImageSuccess(cacheKey, response);
			}
		}, maxWidth, maxHeight, scaleType, Config.RGB_565, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				onGetImageError(cacheKey, error);
			}
		});
	}

	/**
	 * Sets the amount of time to wait after the first response arrives before
	 * delivering all responses. Batching can be disabled entirely by passing in
	 * 0.
	 * 
	 * @param newBatchedResponseDelayMs
	 *            The time in milliseconds to wait.
	 */
	public void setBatchedResponseDelay(int newBatchedResponseDelayMs) {
		mBatchResponseDelayMs = newBatchedResponseDelayMs;
	}

	/**
	 * Handler for when an image was successfully loaded.
	 * 
	 * @param cacheKey
	 *            The cache key that is associated with the image request.
	 * @param response
	 *            The bitmap that was returned from the network.
	 */
	protected void onGetImageSuccess(String cacheKey, Bitmap response) {
		// cache the image that was fetched.
		mCache.putBitmap(cacheKey, response);

		// remove the request from the list of in-flight requests.
		BatchedImageRequest request = mInFlightRequests.remove(cacheKey);

		if (request != null) {
			// Update the response bitmap.
			request.mResponseBitmap = response;

			// Send the batched response
			batchResponse(cacheKey, request);
		}
	}

	/**
	 * Handler for when an image failed to load.
	 * 
	 * @param cacheKey
	 *            The cache key that is associated with the image request.
	 */
	protected void onGetImageError(String cacheKey, VolleyError error) {
		// Notify the requesters that something failed via a null result.
		// Remove this request from the list of in-flight requests.
		BatchedImageRequest request = mInFlightRequests.remove(cacheKey);

		if (request != null) {
			// Set the error for this request
			request.setError(error);

			// Send the batched response
			batchResponse(cacheKey, request);
		}
	}

	/**
	 * Container object for all of the data surrounding an image request.
	 */
	public class ImageContainer {
		/**
		 * The most relevant bitmap for the container. If the image was in
		 * cache, the Holder to use for the final bitmap (the one that pairs to
		 * the requested URL).
		 */
		private Bitmap mBitmap;

		private final ImageListener mListener;

		/** The cache key that was associated with the request */
		private final String mCacheKey;

		/** The request URL that was specified */
		private final String mRequestUrl;

		/**
		 * Constructs a BitmapContainer object.
		 * 
		 * @param bitmap
		 *            The final bitmap (if it exists).
		 * @param requestUrl
		 *            The requested URL for this container.
		 * @param cacheKey
		 *            The cache key that identifies the requested URL for this
		 *            container.
		 */
		public ImageContainer(Bitmap bitmap, String requestUrl,
				String cacheKey, ImageListener listener) {
			mBitmap = bitmap;
			mRequestUrl = requestUrl;
			mCacheKey = cacheKey;
			mListener = listener;
		}

		/**
		 * Releases interest in the in-flight request (and cancels it if no one
		 * else is listening).
		 */
		public void cancelRequest() {
			if (mListener == null) {
				return;
			}

			BatchedImageRequest request = mInFlightRequests.get(mCacheKey);
			if (request != null) {
				boolean canceled = request
						.removeContainerAndCancelIfNecessary(this);
				if (canceled) {
					mInFlightRequests.remove(mCacheKey);
				}
			} else {
				// check to see if it is already batched for delivery.
				request = mBatchedResponses.get(mCacheKey);
				if (request != null) {
					request.removeContainerAndCancelIfNecessary(this);
					if (request.mContainers.size() == 0) {
						mBatchedResponses.remove(mCacheKey);
					}
				}
			}
		}

		/**
		 * Returns the bitmap associated with the request URL if it has been
		 * loaded, null otherwise.
		 */
		public Bitmap getBitmap() {
			return mBitmap;
		}

		/**
		 * Returns the requested URL for this container.
		 */
		public String getRequestUrl() {
			return mRequestUrl;
		}
	}

	/**
	 * Wrapper class used to map a Request to the set of active ImageContainer
	 * objects that are interested in its results.
	 */
	private class BatchedImageRequest {
		/** The request being tracked */
		private final Request<?> mRequest;

		/** The result of the request being tracked by this item */
		private Bitmap mResponseBitmap;

		/** Error if one occurred for this response */
		private VolleyError mError;

		/**
		 * List of all of the active ImageContainers that are interested in the
		 * request
		 */
		private final LinkedList<ImageContainer> mContainers = new LinkedList<ImageContainer>();

		/**
		 * Constructs a new BatchedImageRequest object
		 * 
		 * @param request
		 *            The request being tracked
		 * @param container
		 *            The ImageContainer of the person who initiated the
		 *            request.
		 */
		public BatchedImageRequest(Request<?> request, ImageContainer container) {
			mRequest = request;
			mContainers.add(container);
		}

		/**
		 * Set the error for this response
		 */
		public void setError(VolleyError error) {
			mError = error;
		}

		/**
		 * Get the error for this response
		 */
		public VolleyError getError() {
			return mError;
		}

		/**
		 * Adds another ImageContainer to the list of those interested in the
		 * results of the request.
		 */
		public void addContainer(ImageContainer container) {
			mContainers.add(container);
		}

		/**
		 * Detatches the bitmap container from the request and cancels the
		 * request if no one is left listening.
		 * 
		 * @param container
		 *            The container to remove from the list
		 * @return True if the request was canceled, false otherwise.
		 */
		public boolean removeContainerAndCancelIfNecessary(
				ImageContainer container) {
			mContainers.remove(container);
			if (mContainers.size() == 0) {
				mRequest.cancel();
				return true;
			}
			return false;
		}
	}

	/**
	 * Starts the runnable for batched delivery of responses if it is not
	 * already started.
	 * 
	 * @param cacheKey
	 *            The cacheKey of the response being delivered.
	 * @param request
	 *            The BatchedImageRequest to be delivered.
	 */
	private void batchResponse(String cacheKey, BatchedImageRequest request) {
		mBatchedResponses.put(cacheKey, request);
		if (mRunnable == null) {
			mRunnable = new Runnable() {
				@Override
				public void run() {
					for (BatchedImageRequest bir : mBatchedResponses.values()) {
						for (ImageContainer container : bir.mContainers) {
							if (container.mListener == null) {
								continue;
							}
							if (bir.getError() == null) {
								container.mBitmap = bir.mResponseBitmap;
								container.mListener
										.onResponse(container, false);
							} else {
								container.mListener.onErrorResponse(bir
										.getError());
							}
						}
					}
					mBatchedResponses.clear();
					mRunnable = null;
				}

			};
			// Post the runnable.
			mHandler.postDelayed(mRunnable, mBatchResponseDelayMs);
		}
	}

	private void throwIfNotOnMainThread() {
		if (Looper.myLooper() != Looper.getMainLooper()) {
			throw new IllegalStateException(
					"ImageLoader must be invoked from the main thread.");
		}
	}

	/**
	 * Creates a cache key for use with the L1 cache.
	 * 
	 * @param url
	 *            The URL of the request.
	 * @param maxWidth
	 *            The max-width of the output.
	 * @param maxHeight
	 *            The max-height of the output.
	 * @param scaleType
	 *            The scaleType of the imageView.
	 */
	private static String getCacheKey(String url, int maxWidth, int maxHeight,
			ScaleType scaleType) {
		return new StringBuilder(url.length() + 12).append("#W")
				.append(maxWidth).append("#H").append(maxHeight).append("#S")
				.append(scaleType.ordinal()).append(url).toString();
	}
}
