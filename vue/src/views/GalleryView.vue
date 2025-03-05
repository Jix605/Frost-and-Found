<template>
  <div class="gallery-view">
    <h1>Gallery</h1>
    <div class="gallery-container">
      <img :src="galleryImages[currentImage]" alt="Gallery Image" class="gallery-image" />
    </div>
    <p class="gallery-description">Enjoy our selection of delicious cakes!</p>

    <!-- Display the same images below the slideshow -->
    <div class="below-slideshow-images">
      <img
        v-for="(image, index) in galleryImages"
        :key="index"
        :src="image"
        alt="Gallery Image"
        class="below-image"
      />
    </div>
  </div>
</template>

<script>
import galleryImage1 from '@/assets/gallery1.jpg';
import galleryImage2 from '@/assets/gallery2.jpg';
import galleryImage3 from '@/assets/gallery3.jpg';
import galleryImage4 from '@/assets/gallery4.jpg';
import galleryImage5 from '@/assets/gallery5.jpg';
import galleryImage6 from '@/assets/gallery6.jpg';
import galleryImage7 from '@/assets/gallery7.jpg';
import galleryImage8 from '@/assets/gallery8.jpg';
import galleryImage9 from '@/assets/gallery9.jpg';
import galleryImage10 from '@/assets/gallery10.jpg';
import galleryImage11 from '@/assets/gallery11.jpg';
import galleryImage12 from '@/assets/gallery12.jpg';

export default {
  data() {
    return {
        // Array of images
      galleryImages: [
        galleryImage1, galleryImage2, galleryImage3,
        galleryImage4, galleryImage5, galleryImage6,
        galleryImage7, galleryImage8, galleryImage9,
        galleryImage10, galleryImage11, galleryImage12
      ],
      currentImage: 0,  // Track the current image in the slideshow
    };
  },
  mounted() {
    // Start the slideshow once the component is mounted
    this.startSlideshow();
  },
  methods: {
    // Start the slideshow (automatic image change every 3 seconds)
    startSlideshow() {
      setInterval(() => {
        this.currentImage = (this.currentImage + 1) % this.galleryImages.length;
      }, 3000); // Change image every 3 seconds
    }
  }
};
</script>

<style scoped>
.gallery-view {
  text-align: center;
  padding: 20px;
}

.gallery-image {
  width: 80%;
  max-width: 800px;
  height: auto;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.gallery-description {
  margin-top: 20px;
  font-size: 18px;
  color: #555;
}
/* Style for images below the slideshow */
.below-slideshow-images {
  justify-content: center;
  margin-top: 20px;
}

.below-image {
  width: 100px;
  height: 75px;
  object-fit: cover;
  margin: 0 10px;
  border-radius: 5px;
}
</style>