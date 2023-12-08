package test.teamfresh.dawnmarket.utils

import android.content.Context
import android.widget.Toast
import test.teamfresh.dawnmarket.R

fun toastDevelop(context: Context) = Toast.makeText(context, context.getString(R.string.be_developed), Toast.LENGTH_SHORT).show()

fun toastAddCart(text: String, context: Context) = Toast.makeText(context, text.addPostposition("이 ", "가 ") + context.getString(R.string.add_card_toast), Toast.LENGTH_SHORT).show()