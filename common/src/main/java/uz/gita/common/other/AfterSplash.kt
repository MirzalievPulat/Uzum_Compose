package uz.gita.common.other

enum class AfterSplash(val screen: Int) {
    SIGN_UP(0), SIGN_IN(1), PIN(2)
}

enum class GenderType(val gender:Int){
    MALE(0),FEMALE(1)
}

enum class CardsType(){
    RECENT, MY_CARDS, BY_SEARCH
}