<template>
    <div class="search-container">
      <input
        type="text"
        v-model="keyword"
        placeholder="Search stories..."
        @input="searchStories"
      />
      <div v-if="loading" class="loading">Loading...</div>
      <div v-if="!loading && stories.length > 0" class="search-results">
        <div v-for="story in stories" :key="story.storyID" class="search-item">
          <h3 @click="goToStory(story)">{{ story.title }}</h3>
          <p>{{ story.body.substring(0, 150) }}...</p>
        </div>
      </div>
      <div v-if="!loading && stories.length === 0" class="no-results">
        No stories found.
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        keyword: '',
        stories: [],
        loading: false
      };
    },
    methods: {
      async searchStories() {
        if (this.keyword.trim() === '') {
          this.stories = [];
          return;
        }
        this.loading = true;
        try {
          const response = await axios.get(`http://localhost:8080/stories/search?keyword=${this.keyword}`);
          this.stories = response.data;
        } catch (error) {
          console.error('Error searching stories:', error);
        } finally {
          this.loading = false;
        }
      },
      goToStory(story) {
        this.$router.push({ name: 'StoryDetail', params: { id: story.storyID } });
      }
    }
  };
  </script>
  
  <style scoped>
  .search-container {
    max-width: 600px;
    margin: 20px auto;
    padding: 10px;
  }
  
  .search-container input {
    width: 100%;
    padding: 10px;
    margin-bottom: 20px;
  }
  
  .loading {
    text-align: center;
    font-size: 1.5em;
    color: #333;
  }
  
  .search-results {
    border-top: 1px solid #ddd;
  }
  
  .search-item {
    padding: 10px 0;
    border-bottom: 1px solid #ddd;
  }
  
  .search-item h3 {
    cursor: pointer;
    color: #28666e;
  }
  
  .search-item h3:hover {
    text-decoration: underline;
  }
  
  .no-results {
    text-align: center;
    font-size: 1.2em;
    color: #777;
  }
  </style>
  