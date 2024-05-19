<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div>
    <div class="card-grid">
      <div v-for="story in stories" :key="story.storyID">
        <CCard class="story-card">
          <CCardImage class="card-image" orientation="top" src="https://tinyurl.com/basicimagevute" />
          <CCardBody>
            <CCardTitle class="card-title">{{ story.title }}</CCardTitle>
            <CCardText class="card-text">{{ truncateText(story.body, 50) }}</CCardText>
            <router-link :to="{ name: 'StoryDetail', params: { id: story.storyID } }">
              <CButton class="btn-primary">Go to Story</CButton>
            </router-link>
          </CCardBody>
        </CCard>
      </div>
    </div>
  </div>
</template>

<script>
import { CCard, CCardImage, CCardBody, CCardTitle, CCardText, CButton } from '@coreui/vue';

export default {
  components: {
    CCard,
    CCardImage,
    CCardBody,
    CCardTitle,
    CCardText,
    CButton
  },
  data() {
    return {
      stories: []
    };
  },
  mounted() {
    this.fetchStories();
  },
  methods: {
    async fetchStories() {
      try {
        const response = await fetch('http://localhost:8080/stories');
        if (response.ok) {
          this.stories = await response.json();
        } else {
          throw new Error('Failed to fetch stories');
        }
      } catch (error) {
        console.error("There was an error!", error);
      }
    },
    truncateText(text, length) {
      if (text.length <= length) {
        return text;
      }
      return text.substring(0, length) + '...';
    },
    formatDate(date) {
      return new Date(date).toLocaleDateString("en-GB");
    }
  }
};
</script>

<style>
.card-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(18rem, 1fr));
  gap: 20px;
}

.story-card {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.card-image {
  display: block;
  margin: 0 auto;
  max-width: 100%;
}

.card-title {
  font-size: 1.5em;
  font-weight: bold;
}

.card-text {
  font-size: 0.875em;
}

.btn-primary {
  margin-top: 10px;
}
</style>
