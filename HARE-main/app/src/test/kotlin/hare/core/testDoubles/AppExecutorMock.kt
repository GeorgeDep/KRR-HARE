package hare.core.testDoubles

import hare.core.AppExecutor
import hare.core.AppMode

class AppExecutorMock {
    // MARK: - Public properties

    var mockedResponse: AppExecutor? = null
    var requests = mutableListOf<AppMode?>()

    // MARK: - Public methods

    fun build(appMode: AppMode?): AppExecutor? {
        requests.add(appMode)
        return mockedResponse
    }
}