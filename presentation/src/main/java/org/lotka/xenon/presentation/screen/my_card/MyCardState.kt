package org.lotka.xenon.presentation.screen.my_card

import org.lotka.xenon.domain.model.Item

data class MyCardState(

   val items: List<Item> = emptyList()
)