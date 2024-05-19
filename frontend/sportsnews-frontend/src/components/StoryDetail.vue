<template>
  <div v-if="loading" class="loading">Loading...</div>
  <div v-else class="story-detail">
    <img :src="story.imageURL || 'https://tinyurl.com/basicimagevute'" alt="Story Image" class="story-image" />
    <h1 class="story-title">{{ story.title }}</h1>
    <p class="story-body">{{ story.body }}</p>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

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
      loading: true
    };
  },
  mounted() {
    this.fetchStory();
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

.loading {
  text-align: center;
  font-size: 1.5em;
  color: #333;
  margin: 80px auto;
}
</style>
