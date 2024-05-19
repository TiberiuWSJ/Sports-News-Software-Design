<template>
  <div class="admin-container">
    <h1>Admin Dashboard</h1>
    <div class="admin-buttons">
      <button @click="navigateTo('ManageUsers')">Manage Users</button>
      <button @click="navigateTo('ManageStories')">Manage Stories</button>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'AdminPage',
  methods: {
    navigateTo(page) {
      this.$router.push({ name: page });
    }
  }
};
</script>

<style scoped>
.admin-container {
  padding: 20px;
  text-align: center;
  margin-top: 60px; /* Add this line */
}

.admin-buttons {
  margin-bottom: 20px;
}

button {
  padding: 10px 20px;
  margin: 10px;
  cursor: pointer;
}
</style>
