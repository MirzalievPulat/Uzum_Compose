package uz.gita.domain.useCase

import uz.gita.domain.model.request.AfterSplash

interface GetNextScreenUC {
    operator fun invoke(): AfterSplash
}