<template>
  <div v-if="loading" class="loading">Loading...</div>
  <div v-else class="story-detail">
    <img :src="story.imageURL || 'https://tinyurl.com/basicimagevute'" alt="Story Image" class="story-image" />
    <h1 class="story-title">{{ story.title }}</h1>
    <p class="story-body">{{ story.body }}</p>
    <button :disabled="isFavorite" @click="addToFavorites" class="favorite-button">
      <i class="fas fa-heart"></i> <span>{{ isFavorite ? '  Added to Favorites' : '  Add to Favorites' }}</span>
    </button>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import axios from 'axios';

interface Story {
  imageURL: string;
  title: string;
  body: string;
}

export default defineComponent({
  props: {
    id: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      story: null as Story | null,
      loading: true,
      isFavorite: false // Track if the story is already a favorite
    };
  },
  mounted() {
    this.fetchStory();
    this.checkIfFavorite();
  },
  methods: {
    async fetchStory() {
      try {
        const response = await fetch(`http://localhost:8080/stories/${this.id}`);
        if (response.ok) {
          this.story = await response.json();
          this.loading = false;
        } else {
          throw new Error('Failed to fetch story');
        }
      } catch (error) {
        console.error("There was an error!", error);
        this.loading = false;
      }
    },
    async checkIfFavorite() {
      try {
        const userId = localStorage.getItem('userId');
        const response = await axios.get(`http://localhost:8080/favorites/user/${userId}`);
        const favorites = response.data;
        this.isFavorite = favorites.some((favorite: Story) => favorite.storyID === parseInt(this.id));
      } catch (error) {
        console.error('Error checking favorite status:', error);
      }
    },
    async addToFavorites() {
      try {
        const userId = localStorage.getItem('userId');
        await axios.post(`http://localhost:8080/favorites/user/${userId}/story/${this.id}`);
        this.isFavorite = true;
      } catch (error) {
        console.error('Error adding to favorites:', error);
      }
    }
  }
});
</script>

<style scoped>
.story-detail {
  max-width: 800px;
  margin: 80px auto 20px;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  background-color: #fff;
}

.story-image {
  width: 100%;
  height: auto;
  margin-bottom: 20px;
  border-radius: 8px;
}

.story-title {
  font-size: 2em;
  margin-bottom: 20px;
  color: #333;
  text-align: center;
  word-break: break-word;
  hyphens: auto;
  white-space: normal;
  overflow-wrap: break-word;
  padding: 0 10px;
}

.story-body {
  font-size: 1em;
  color: #555;
  line-height: 1.6;
  text-align: justify;
}

.favorite-button {
  display: inline-flex;
  align-items: center;
  background-color: #007bff;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 20px;
}

.favorite-button[disabled] {
  background-color: #6c757d;
  cursor: not-allowed;
}

.favorite-button i {
  margin-right: 8px; /* Adjust the space as needed */
}

.loading {
  text-align: center;
  font-size: 1.5em;
  color: #333;
  margin: 80px auto;
}
</style>
