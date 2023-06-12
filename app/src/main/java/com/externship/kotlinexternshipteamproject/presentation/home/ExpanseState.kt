package com.externship.kotlinexternshipteamproject.presentation.home

import com.externship.kotlinexternshipteamproject.domain.model.Expanse

data class ExpanseState(
    val expanses: List<Expanse> = emptyList()
)