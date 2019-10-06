<template>
  <div id="app">
    <b-row>
      <b-col lg="4" class="pb-2"></b-col>
      <b-col lg="4" class="pb-2">
        <bookSearch></bookSearch>
      </b-col>
      <b-col lg="4" class="pb-2">
        <b-row align-h="end">
          <p v-if="this.authenticated === 'true'">{{ this.username }}</p>
          <b-button size="sm" v-if="this.authenticated === 'true'" @click="signOut">Sign Out</b-button>
          <signInModal v-if="this.authenticated != 'true'"></signInModal>
          <signUpModal v-if="this.authenticated != 'true'"></signUpModal>
        </b-row>
      </b-col>
    </b-row>
    <b-row>
      <b-col lg="8" class="pb-2">
        <bookGrid></bookGrid>
      </b-col>
      <b-col lg="4" class="pb-2">
        <bookSearchHistory></bookSearchHistory>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import signInModal from './components/SignInModal'
import signUpModal from './components/SignUpModal'
import bookSearch from './components/BookSearch'
import bookGrid from './components/BookGrid'
import bookSearchHistory from './components/BookSearchHistory'

export default {
  name: 'App',
  components: {
    signInModal,
    signUpModal,
    bookSearch,
    bookGrid,
    bookSearchHistory
  },
  data () {
    return {
      username: '',
      authenticated: 'false'
    }
  },
  methods: {
    signIn (username) {
      this.username = username
      this.authenticated = 'true'
    },
    signOut () {
      alert('로그아웃 성공')
      this.username = ''
      this.authenticated = 'false'
      localStorage.removeItem('username')
      localStorage.removeItem('authenticated')
      this.$EventBus.$emit('signedOut', this.authenticated)
    }
  },
  mounted () {
    this.$EventBus.$on('signedIn', this.signIn)
    if (localStorage.authenticated) {
      this.username = localStorage.username
      this.authenticated = localStorage.authenticated
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
