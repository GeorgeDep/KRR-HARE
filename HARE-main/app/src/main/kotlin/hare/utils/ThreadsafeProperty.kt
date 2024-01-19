package hare.utils

class ThreadsafeProperty<Type>(initialValue: Type) {
    // MARK: - Public properties

    @Volatile
    var value: Type = initialValue
        private set

    // MARK: - Public methods

    fun set(value: Type) { this.value = value }
}