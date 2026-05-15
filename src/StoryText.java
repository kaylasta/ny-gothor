/**
 * Holds all narrative text for the game. Keeping story content separate from
 * logic makes it easy to edit writing without touching game code.
 */
public class StoryText {

    // -------------------------------------------------------------------------
    // Introduction
    // -------------------------------------------------------------------------

    public static final String[] INTRODUCTION = {
        "You are part of a small, close-knit group of friends, seeking solace in the Appalachian wilds. "
            + "Packed for a month-long expedition, you set out at the dawn of autumn, the perfect time to witness the leaves turning fiery hues.",

        "You park your truck and begin walking north.",

        "After venturing for two weeks, the idea of wandering off the trail becomes a common sentiment among the group. "
            + "This deviation, initially undertaken with idle curiosity, soon turns into an unintended foray into regions older than memory. "
            + "Trees of unnatural thickness loom overhead, their coiling branches forming grotesque, whispering arches beneath which you walk. "
            + "Everyone acknowledges the heavy feeling in the air, though no one speaks of it. You venture deeper into the wilderness, straying further from the path.",

        "Two days have passed since you left the trail. Formations of rocks — perhaps carved in forgotten epochs by hands long since turned to dust — lie "
            + "scattered among the descending hills in a way that feels intentional.",

        "The aeolian sounds passing through the trees ceased yesterday, leaving only the sound of your group's idle chatter "
            + "and the dry scrape of boots against stone.",

        "The sun, already low, sinks behind the jagged peaks with unnatural speed, casting shadows that twist and distort the dying light. "
            + "It is in this half-light, when the pallid sky takes on a sickly hue, that you feel a calling to the stones. Curiosity urges you to investigate, and so you do. "
            + "The ground softens beneath your feet as you wander from the group, and then it gives way entirely, sending you tumbling into an abyss.",

        "You awake, vision blurry. Looking up you see how far you have fallen. "
            + "Any chance of climbing back is already rendered impossible. Viewing your surroundings you notice two paths in this cavern."
    };

    // -------------------------------------------------------------------------
    // Altar room
    // -------------------------------------------------------------------------

    public static final String[] ALTAR_INTRO = {
        "In the depths of this accursed cavern the atmosphere grows dense, almost choking you, and a green light emanates from some cubic monolith.",
        "Stalactites hang around this centrepiece in an unnatural pattern, curving into it like they are being dragged into its mass.",
        "This cube, wrought of stone darker than void, bears ancient symbols, cryptic and blasphemous.",
        "A faint whisper parades across the room to your ears, spiralling your mind into unease.",
        "Upon the cube lays a scroll. Its text uncomprehensible yet literate."
    };

    public static final String ALTAR_ALREADY_SPOKEN =
        "You leave the room having already spoken the text.";

    public static final String ALTAR_SPEAK =
        "You speak the text and feel a wave of unknowing wash over your mind.";

    public static final String ALTAR_REFUSE =
        "You think it wise to not speak these words and leave.";

    // -------------------------------------------------------------------------
    // Ny'Gothor encounter
    // -------------------------------------------------------------------------

    public static final String[] NY_GOTHOR_SHARED = {
        "As you wander deeper into the cavern you find your spirit, your very soul, weighed down by the air — as if a dark blanket lay upon you.",
        "The passage seems to twist unnaturally in a pattern mimicking that of a spiral, as though reality itself grows pliable.",
        "The stones around you grow darker till they become uniform, only identifiable by the starry reflection cast upon them by your ever weakening light.",
        "The passage begins to widen as a cacophonous sound shakes your mind.",
        "Before you lies an abomination that words of this language cannot describe — a being whose very existence defies the fragile laws of the world you thought you knew.",
        "Its form writhes and shifts ceaselessly, an entropic mass of tendrils and limbs oscillating with no logical pattern.",
        "Your eyes, though terrified to bear witness, catch glimpses of numerous orbs glowing with an unnatural, malignant light.",
        "These eyes — if eyes they can be called — stare through you, as if they perceive more than your flesh, as if they perceive the very essence of your existence.",
        "The air here hums as if it were in pain, bending to the will of the creature.",
        "The walls of this chamber could not be discerned from that of the night sky — calling them cosmic would hardly describe it — yet through your paralysed gaze you notice carvings of ancient symbols, older than humanity itself, pulsing."
    };

    public static final String[] NY_GOTHOR_WITH_INCANTATION = {
        "You feel these markings twist your perception but you maintain your grasp on reality. Beyond the creature, at the farthest edge of the cavern, the abyss yawns wide.",
        "It cannot be defined as a chasm, rather a gaping void revealing the infinite darkness beyond. Your eyes get caught in this void, unable to deter the thought that something far greater lies within.",
        "You feel a pull of madness there, a beckoning from the cosmos that promises knowledge — knowledge that will unravel your very soul.",
        "You realise with sickening clarity that you are no longer a part of the world you once knew. You are but a fleeting speck before the vast forces that lurk beyond the stars…",
        "Yet this does not deter you."
    };

    public static final String[] NY_GOTHOR_WITHOUT_INCANTATION = {
        "These markings twist your perception, filling your mind with fleeting glimpses of incomprehensible worlds beyond the veil of sanity. Voices whisper in your mind. You cannot understand what it is they speak of, yet their intent is clear.",
        "Your body begins to move further into the chamber despite your attempts not to. The floor beneath you feels strangely soft, as if the stone itself is decaying. The voices grow louder.",
        "You move to the centre of the room and notice you stand amongst a series of concentric rings. The creature looms over you as your vision begins to merge with the unknown.",
        "You drop to your knees as the voices scream at you. Reaching into your back pocket you grasp onto your pocketknife and open it.",
        "You penetrate your skin at the neck."
    };

    // -------------------------------------------------------------------------
    // Endings
    // -------------------------------------------------------------------------

    public static final String[] ENDING_SHARED = {
        "The beast lies slain, a twisted ruin of blood and viscera strewn across the cavern floor, its unnatural form now a grotesque memory.",
        "A chill wind sighs from the abyssal rift, carrying with it a disquieting resonance.",
        "The corpse succumbs to the breeze, unravelling with unnatural haste into nothingness, its departure leaving behind an acrid tang that clings to the air."
    };

    public static final String[] ENDING_GOOD = {
        "In its absence, the shroud of darkness lifts, revealing a passage concealed in the stone — a crack holding a faint luminescence.",
        "Driven by desperation to leave this place, you drag your battered body toward the opening. At its base, a flight of narrow steps spirals upward, their uneven contours carved with irregularity.",
        "With no other recourse you brace yourself and begin the climb. Time bleeds into insignificance, and the journey becomes a blur of strained breath and trembling limbs.",
        "It is as though the staircase itself conspires against you, extending its winding path far beyond comprehension.",
        "At last, the oppressive dark yields to a blinding radiance. Sunlight strikes your face with an almost alien warmth, a piercing contrast to the cold of the depths below.",
        "The wind, no longer heavy with subterranean whispers, now howls clean and sharp. Blinking against the brilliance, you emerge from a jagged fissure in a mountainside, hidden amidst a tangle of ancient stones.",
        "Before you sprawls a valley cloaked in golden light, its contours familiar yet tinged with an uncanny, dreamlike haze. The sun hangs low.",
        "Upon closer inspection you realise where you are. You know the way home."
    };

    public static final String[] ENDING_BAD = {
        "But the wind does not stop. It whispers, subtle yet unstoppable, threading into your mind with a vile intimacy. The murmurs slither like tendrils, pressing against the fragile walls of your sanity.",
        "They speak no nameable language, yet their meaning saturates your being: surrender, descend, obey.",
        "Your limbs betray you, moving as though guided by an unseen puppeteer. The whispers do not shout, for they have no need. You are but a vessel now, your will frail and broken.",
        "The abyss yawns wide before you.",
        "And then you fall.",
        "Not with the terror of one cast into darkness, but with the terrible certainty of one fulfilling a long-ordained purpose. The air grows thick, cloying with the scent of decay.",
        "The whispers swell to a symphony of triumph, their meaning now crystal-clear: there is no escape.",
        "There never was."
    };

    // -------------------------------------------------------------------------
    // Room path descriptions
    // -------------------------------------------------------------------------

    public static final String[] PATH_DESCRIPTIONS = {
        "A path descends into an abyssal gloom, where the walls seem to pulsate with a loathsome, unseen life. Strange symbols writhe faintly upon the stone, mocking your sanity.",
        "A narrow passage coils through the rock, its air heavy with the scent of decay and ancient dust. Faint whispers, carried by no discernible wind, beckon from the unseen recesses.",
        "A corridor of jagged stone, its surface slick with a viscous, black substance that reflects the feeble light. The walls seem to close in, as though alive.",
        "The cavern yawns open, its towering stalactites resembling the fangs of some primordial beast. Shadows dance erratically across the ground, though no flame illuminates the space.",
        "A passage winds in a serpentine manner, constricting like the coils of a serpent. An oppressive silence fills the space, broken only by the faint sound of dripping water far in the unseen depths.",
        "A tunnel twists unnervingly, its walls seeming to ripple like water disturbed by some unseen force. The floor looks wrong, as though it shifts slightly, responding to your presence.",
        "A stifling darkness envelops the corridor ahead, the kind that seems to swallow light whole. Faint, echoing sounds drift through the space — whether the cries of distant explorers or the last gasps of something far more ancient and terrible, you cannot tell.",
        "The path curves sharply here, vanishing into a maw of impenetrable shadow. A faint glow emanates from the stone, though its source is unknown, and the oppressive atmosphere suggests something old.",
        "A low, steady hum resonates through the tunnel, as if the earth itself sings an alien hymn. The walls are covered in strange, phosphorescent fungi that cast an unholy light, illuminating strange patterns in the rock."
    };

    // -------------------------------------------------------------------------
    // Room descriptions
    // -------------------------------------------------------------------------

    public static final String[] ROOM_DESCRIPTIONS = {
        "The chamber is vast, its uneven walls appear carved by some long-forgotten force. A sickly green light emanates from strange, rune-covered stones embedded in the floor. The air is thick with the scent of rot and something far older, as if the room itself is alive.",
        "You find yourself in a low-ceilinged room, the oppressive weight of centuries pressing down from above. Faded murals cover the walls, depicting twisted forms locked in eternal torment, their eyes following you with an unsettling awareness.",
        "The room's walls are slick with moisture, unnaturally cold, as though the very stone rejects the warmth of life. Piles of ancient bones, bleached and brittle, litter the floor.",
        "The chamber opens into a grand, circular space, the ceiling lost in shadows far above. In the centre stands an altar of black stone, its surface etched with unreadable glyphs that seem to pulse faintly in the dim light.",
        "The room is unnervingly symmetrical, every corner too sharp, every line too perfect. The air buzzes with a low, almost imperceptible hum, while a faint vibration runs through the stone floor.",
        "The space is cramped, suffocating even, with the ceiling sagging as though the weight of countless eons threatens to crush all within. In the corner a mound of strange, decayed fabric lies, its contents shifting ever so slightly.",
        "This room feels wrong, as if it exists in defiance of natural law. The air is dense, thick with a palpable sense of unease. Strange angular shapes cover the walls — symbols that seem to shift and change when viewed out of the corner of your eye. The ceiling drips with a viscous fluid that evaporates before reaching the ground, leaving the room in a constant state of strange anticipation.",
        "A circular pit dominates the centre of the room, surrounded by jagged, unnatural formations that seem to grow from the floor. The pit is impossibly deep, and from within it rises a foul, cloying mist that carries with it whispers — faint and indistinct, but unmistakably filled with fear and despair.",
        "The room is vast, but the oppressive darkness swallows all but the immediate space around you. The ground beneath your feet is uneven, as though the floor is slowly warping under the weight of something beyond human reckoning.",
        "A faint blue light seeps into the room from an unknown source, casting long, distorted shadows across the floor. The walls are etched with strange geometric patterns that seem to lead your eye in circles, drawing you deeper into their maddening design."
    };

    // Stop instantiation
    private StoryText() {}
}