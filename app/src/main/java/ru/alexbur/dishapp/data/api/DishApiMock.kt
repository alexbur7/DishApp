package ru.alexbur.dishapp.data.api

import kotlinx.coroutines.delay
import ru.alexbur.dishapp.data.models.response.DishResponse
import ru.alexbur.dishapp.data.models.response.DishesResponse
import javax.inject.Inject

internal interface DishApi {
    suspend fun getDishes(): DishesResponse
    suspend fun removeDishes(ids: List<String>): DishesResponse
    suspend fun getById(id: String): DishResponse
}

internal class DishApiMock @Inject constructor() : DishApi {

    private var data = DishesResponse(
        listOf(
            DishResponse(
                "5ed8da011f071c00465b2026",
                "Бургер \"Америка\"",
                "320 г • Котлета из 100% говядины (прожарка medium) на гриле, картофельная булочка на гриле, фирменный соус, лист салата, томат, маринованный лук, жареный бекон, сыр чеддер.",
                "https://loremflickr.com/320/240/food?1",
                "259"
            ),
            DishResponse(
                "5ed8da011f071c00465b2027",
                "Бургер \"Мексика\"",
                "295 г • Котлета из 100% говядины (прожарка medium) на гриле, картофельная булочка на гриле, мексиканские чипсы начос, лист салата, перчики халапеньо, сыр чеддер, соус сальса из свежих томатов.",
                "https://loremflickr.com/320/240/food?2",
                "229"
            ),
            DishResponse(
                "5ed8da011f071c00465b2028",
                "Бургер \"Русский\"",
                "460 г • Две котлеты из 100% говядины (прожарка medium) на гриле, картофельная булочка на гриле, фирменный соус, лист салата, томат, маринованный лук, маринованные огурчики, двойной сыр чеддер, соус ранч.",
                "https://loremflickr.com/320/240/food?3",
                "379"
            ),
            DishResponse(
                "5ed8da011f071c00465b2029",
                "Бургер \"Люксембург\"",
                "Куриное филе приготовленное на гриле, картофельная булочка на гриле, сыр чеддер, соус ранч, лист салата, томат, свежий огурец.",
                "https://loremflickr.com/320/240/food?4",
                "189"
            ),
            DishResponse(
                "5ed8da011f071c00465b202a",
                "Бургер \"Классика\"",
                "290 г • Котлета из 100% говядины (прожарка medium) на гриле, картофельная булочка на гриле, фирменный соус, лист салата, томат, сыр чеддер.",
                "https://loremflickr.com/320/240/food?5",
                "199"
            ),
            DishResponse(
                "5ed8da011f071c00465b202b",
                "Бургер \"Швейцария\"",
                "320 г • Котлета из 100% говядины (прожарка medium) на гриле, картофельная булочка на гриле, натуральный сырный соус, лист салата, томат, маринованный огурчик, 2 ломтика сыра чеддер.",
                "https://loremflickr.com/320/240/food?6",
                "279"
            ),
            DishResponse(
                "5ed8da011f071c00465b202e",
                "Пицца Дьябло с двойной начинкой",
                "Бекон, свинина, пепперони, перец болгарский, халапеньо, томатный пицца-соус, соус шрирача, моцарелла, зелень",
                "https://loremflickr.com/320/240/food?7",
                "779"
            ),
            DishResponse(
                "5ed8da011f071c00465b202f",
                "Пицца Карбонара с двойной начинкой",
                "Бекон, пармезан, соус сливочный, моцарелла",
                "https://loremflickr.com/320/240/food?8",
                "739"
            ),
            DishResponse(
                "5ed8da011f071c00465b2030",
                "Пицца Петровская с двойной начинкой",
                "Пепперони, курица, бекон, ветчина, помидоры, шампиньоны, лук красный, моцарелла, укроп",
                "https://loremflickr.com/320/240/food?9",
                "895"
            ),
            DishResponse(
                "5ed8da011f071c00465b2031",
                "Пицца 2 берега с двойной начинкой",
                "Свинина, курица, пепперони, ветчина, бекон, помидоры, перец болгарский, соус барбекю, моцарелла,укроп",
                "https://loremflickr.com/320/240/food?10",
                "899"
            ),
            DishResponse(
                "5ed8da011f071c00465b2032",
                "Пицца Мясная с двойной начинкой",
                "Охотничьи колбаски, курица, свинина, пепперони, шампиньоны, томатный пицца-соус, моцарелла, зелень",
                "https://loremflickr.com/320/240/food?11",
                "895"
            ),
            DishResponse(
                "5ed8da011f071c00465b2033",
                "Пицца Маргарита с двойной начинкой",
                "Моцарелла, помидоры, томатный пицца-соус",
                "https://loremflickr.com/320/240/food?12",
                "524"
            ),
        )
    )

    override suspend fun getDishes(): DishesResponse {
        longWorkEmulation()
        return data
    }

    override suspend fun removeDishes(ids: List<String>): DishesResponse {
        longWorkEmulation()
        return DishesResponse(data.dishes.filter { ids.contains(it.id).not() }).also {
            data = it
        }
    }

    // предполагаем, что при передаче несуществующего id будет ошибка
    override suspend fun getById(id: String): DishResponse {
        longWorkEmulation()
        return data.dishes.first { it.id == id }
    }

    private suspend fun longWorkEmulation() {
        delay(500L)
    }
}