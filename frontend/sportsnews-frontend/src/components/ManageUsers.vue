<template>
  <div class="manage-users">
    <h2>Manage Users</h2>
    <form @submit.prevent="createUser">
      <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" v-model="newUser.email" required />
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="newUser.password" required />
      </div>
      <div class="form-group">
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" v-model="newUser.firstName" required />
      </div>
      <div class="form-group">
        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" v-model="newUser.lastName" required />
      </div>
      <div class="form-group">
        <label for="isModerator">Is Moderator:</label>
        <input type="checkbox" id="isModerator" v-model="newUser.isModerator" />
      </div>
      <div class="form-group">
        <label for="isJournalist">Is Journalist:</label>
        <input type="checkbox" id="isJournalist" v-model="newUser.isJournalist" />
      </div>
      <button type="submit">Create User</button>
    </form>
    <h2>Existing Users</h2>
    <div v-for="user in users" :key="user.id" class="user-item">
      <p>{{ user.firstName }} {{ user.lastName }}</p>
      <div class="actions">
        <button @click="editUser(user)">Edit</button>
        <button @click="deleteUser(user.id)">Delete</button>
      </div>
    </div>

    <!-- Edit User Modal -->
    <div v-if="showModal" class="modal-overlay" @click.self="closeModal">
      <div class="modal">
        <h2>Edit User</h2>
        <form @submit.prevent="updateUser">
          <div class="form-group">
            <label for="editEmail">Email:</label>
            <input type="email" id="editEmail" v-model="currentUser.email" required />
          </div>
          <div class="form-group">
            <label for="editPassword">Password:</label>
            <input type="password" id="editPassword" v-model="currentUser.password" required />
          </div>
          <div class="form-group">
            <label for="editFirstName">First Name:</label>
            <input type="text" id="editFirstName" v-model="currentUser.firstName" required />
          </div>
          <div class="form-group">
            <label for="editLastName">Last Name:</label>
            <input type="text" id="editLastName" v-model="currentUser.lastName" required />
          </div>
          <div class="form-group">
            <label for="editIsModerator">Is Moderator:</label>
            <input type="checkbox" id="editIsModerator" v-model="currentUser.isModerator" />
          </div>
          <div class="form-group">
            <label for="editIsJournalist">Is Journalist:</label>
            <input type="checkbox" id="editIsJournalist" v-model="currentUser.isJournalist" />
          </div>
          <button type="submit">Save Changes</button>
          <button type="button" @click="closeModal">Cancel</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      users: [],
      newUser: {
        email: '',
        password: '',
        firstName: '',
        lastName: '',
        isModerator: false,
        isJournalist: false
      },
      showModal: false,
      currentUser: {}
    };
  },
  mounted() {
    this.fetchUsers();
  },
  methods: {
    async fetchUsers() {
      try {
        const response = await axios.get('http://localhost:8080/users');
        this.users = response.data;
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    },
    async createUser() {
      try {
        const response = await axios.post('http://localhost:8080/users', this.newUser);
        this.users.push(response.data);
        this.newUser = { email: '', password: '', firstName: '', lastName: '', isModerator: false, isJournalist: false };
      } catch (error) {
        console.error('Error creating user:', error);
      }
    },
    async deleteUser(userId) {
      try {
        await axios.delete(`http://localhost:8080/users/${userId}`);
        this.users = this.users.filter(user => user.id !== userId);
      } catch (error) {
        console.error('Error deleting user:', error);
      }
    },
    editUser(user) {
      this.currentUser = { ...user }; // Clone the user object to avoid direct mutation
      this.showModal = true;
    },
    async updateUser() {
      try {
        const response = await axios.put(`http://localhost:8080/users/${this.currentUser.id}`, this.currentUser);
        const index = this.users.findIndex(user => user.id === this.currentUser.id);
        this.users.splice(index, 1, response.data); // Replace the updated user in the list
        this.closeModal();
      } catch (error) {
        console.error('Error updating user:', error);
      }
    },
    closeModal() {
      this.showModal = false;
    }
  }
};
</script>

<style scoped>
.manage-users {
  padding: 20px;
  margin-top: 60px;
}

.form-group {
  margin-bottom: 20px;
}

.user-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.actions {
  display: flex;
  gap: 10px;
}

.actions button {
  padding: 5px 10px;
  cursor: pointer;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
}

.actions button:hover {
  background-color: #0056b3;
}

button {
  padding: 10px 20px;
  cursor: pointer;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  margin-right: 10px;
}

button:hover {
  background-color: #0056b3;
}

/* Modal styles */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal {
  background: #ffffff3f;
  padding: 20px;
  border-radius: 8px;
  max-width: 500px;
  width: 100%;
}

.modal h2 {
  margin-top: 0;
}

.modal .form-group {
  margin-bottom: 20px;
}

.modal button {
  margin-right: 10px;
}
</style>
