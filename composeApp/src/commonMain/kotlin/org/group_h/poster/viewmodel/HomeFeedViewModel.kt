import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryan.poster_app_api_wrapper.ApiClient
import com.ryan.poster_app_api_wrapper.ApiClientSingleton
import com.ryan.poster_app_api_wrapper.HomeFeed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeFeedViewModel(
    private val apiClient: ApiClient = ApiClientSingleton
) : ViewModel() {
    private val _homeFeed = MutableStateFlow<HomeFeed?>(null)
    val homeFeed: StateFlow<HomeFeed?> = _homeFeed

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        println("init")
        if (_homeFeed.value == null) {
            loadHomeFeed()
        }
    }

    fun loadHomeFeed(forceRefresh: Boolean = false) {
        if (_homeFeed.value != null && !forceRefresh) return
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _homeFeed.value = apiClient.getHomeFeed()
            } catch (e: Exception) {
                _homeFeed.value = HomeFeed(
                    posts = emptyList(),
                    message = "caught error",
                    page = 1
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
}

