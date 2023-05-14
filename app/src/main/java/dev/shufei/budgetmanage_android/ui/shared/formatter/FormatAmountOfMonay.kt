package dev.shufei.budgetmanage_android.ui.shared.formatter

fun formatAmountOfMoney(
    amount: Int
): String {
    return "¥${"%,d".format(amount)}"
}
