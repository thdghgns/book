<template>
  <div>
    <b-row>
          <b-col lg="10" class="pb-2"><b-form-input v-model="keyword" placeholder="search books..." @keyup.enter="bookSearch(1)" ></b-form-input></b-col>
          <b-col lg="2" class="pb-2"><b-button variant="primary" @click="bookSearch(1)">search</b-button></b-col>
    </b-row>
  </div>
</template>

<script>
export default {
  name: 'BookSearch',
  data () {
    return {
      keyword: ''
    }
  },
  methods: {
    bookSearch (page) {
      let axiosConfig = {
        headers: {
          'Content-Type': 'application/json;charset=UTF-8'
        }
      }

      this.$Axios.get('http://localhost:8080/books?keyword=' + encodeURIComponent(this.keyword) + '&page=' + page, axiosConfig)
        .then(response => {
          let responseData = response.data
          this.$EventBus.$emit('drawGrid', responseData)
          this.$EventBus.$emit('refreshRank', responseData)
          this.$EventBus.$emit('refreshHistory', responseData)
        })
        .catch(error => {
          if (error.response) {
            console.log(error.response.data)
            console.log(error.response.status)
            console.log(error.response.headers)
          }
        })
    }
  },
  mounted () {
    this.$EventBus.$on('searchPage', this.bookSearch)
  }
}
</script>

<style scoped>

</style>
