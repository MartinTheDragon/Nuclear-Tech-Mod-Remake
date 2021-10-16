package at.martinthedragon.nucleartech.hazards.items

interface HazardItemEffect {
    // TODO

    companion object {
        /*
        Radiation value constants
        Item radiation, block radiation, etc. values are derived from these
        Sorted by order in the periodic table
        An 'F' at the end means the value is for Fuel
        */

        const val Co60 = 30F // Cobalt
        const val Tc99 = 2.75 // Technetium
        const val I131 = 150F // Iodine
        const val Xe135 = 1250F // Xenon
        const val Cs137 = 20F // Caesium
        const val Au198 = 500F // Gold
        const val At209 = 2000F // Astatine
        const val Po210 = 75F // Polonium
        const val Ra226 = 7.5F // Radon
        const val Th232 = .1F // Thorium
        const val ThF = 1.75F
        const val U = .35F // Uranium
        const val U233 = 5F
        const val U235 = 1F
        const val U238 = .25F
        const val UF = .5F
        const val Np237 = 2.5F // Neptunium
        const val NpF = 1.5F
        const val Pu = 7.5F // Plutonium
        const val Pu238 = 10F
        const val Pu239 = 5F
        const val Pu240 = 7.5F
        const val Pu241 = 25F
        const val PuF = 4.25F
        const val Am241 = 8.5F // Americium
        const val Am242 = 9.5F
        const val AmF = 4.75F

        // Other

        const val MOX = 2.5F
        const val Trinitite = .1F
        const val Waste = 15F

        // Fictional

        const val Sa326 = 15F // Schrabidium
        const val Sa327 = 17.5F // Solinium
        const val SaF = 5.85F
        const val Sr = Sa326 * .1F // Schraranium
    }
}
