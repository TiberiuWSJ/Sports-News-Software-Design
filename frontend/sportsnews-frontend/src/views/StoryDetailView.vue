<script setup lang="ts">
import { useRoute } from 'vue-router';
import StoryDetail from '../components/StoryDetail.vue';
import Navbar from '../components/Navbar.vue';

// Get the route object
const route = useRoute();

// Extract the 'id' parameter from the route
const id = route.params.id as string;
</script>

<template>
  <main>
    <Navbar />
    <!-- Pass the id prop to the StoryDetail component -->
    <StoryDetail :id="id" />
  </main>
</template>

<style>
main {
  width: auto;
  height: auto;
}
</style>
