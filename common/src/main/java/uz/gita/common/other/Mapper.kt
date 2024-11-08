package uz.gita.common.other

import uz.gita.common.data.CardData

fun CardData.CardInfo.toParams():CardData.UpdateCardParams{
    return CardData.UpdateCardParams(id,name,themeType,isVisible)
}