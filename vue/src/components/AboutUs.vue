<template>
    <div class="about-us">
        <div class="about-header">
            <h1>About Frost & Found Bakery</h1>
            <p>Your go-to destination for delicious and beautifully crafted cakes.</p>
        </div>
        <div class="about-content">
            <div class="about-text">
                <p>
                    At **Frost & Found**, we specialize in creating the finest cakes for every occasion.
                    Whether it's a birthday, wedding, or simply a special treat for yourself, we ensure
                    that every bite is an unforgettable experience.
                </p>
                <p>
                    Our cakes are made with the finest ingredients, lovingly baked with a lot of care, and decorated
                    with
                    creativity and precision.
                    From traditional flavors to custom designs, we offer an array of cakes that can fit any event or
                    celebration.
                </p>
                <p>
                    What sets us apart is not just the taste, but the passion we put into each creation.
                    We believe that a cake should not only taste amazing but also tell a story through its design.
                </p>
            </div>

            <div class="about-image">
                <img :src="aboutImage" alt="Frost & Found Bakery" />
            </div>
        </div>

        <mission-statement />
    </div>
</template>

<script>
import aboutImage from "@/assets/aboutus.jpg";
import MissionStatement from "../components/MissionStatement.vue";

export default {
    data() {
        return {
            aboutImage,
        };
    },
    components: {
        MissionStatement
    }
}
</script>