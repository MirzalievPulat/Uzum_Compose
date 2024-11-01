package uz.gita.domain.authUseCase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import uz.gita.common.data.AuthData
import uz.gita.entity.repository.AuthRepository
import uz.gita.domain.authUseCase.SignUpUC
import javax.inject.Inject

class SignUpUCImpl @Inject constructor(private val repository: AuthRepository) : SignUpUC {
    override fun invoke(signUp: AuthData.SignUp): Flow<Result<Unit>> = flow {
        val result = repository.signUp(signUp)
        emit(result)
    }.catch { emit(Result.failure(it)) }
}