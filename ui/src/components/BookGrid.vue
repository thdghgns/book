<template>
  <div>
    <b-table
      id="book-table" striped hover
      :items="items"
      :fields="fields"
      @row-clicked="expandAdditionalInfo">

      <template slot="row-details" slot-scope="row">
        <b-card no-body class="overflow-hidden">
          <b-row no-gutters>
            <b-col md="2">
              <b-img thumbnail :src="row.item.thumbnailLink" class="rounded-0"></b-img>
            </b-col>
            <b-col md="10">
              <b-card-body>
                <b-table-simple stacked>
                  <b-thead>
                    <b-tr>
                      <b-th>isbn</b-th>
                      <b-th>title</b-th>
                      <b-th>author</b-th>
                      <b-th>description</b-th>
                      <b-th>publisher</b-th>
                      <b-th>publishDate</b-th>
                      <b-th>price</b-th>
                      <b-th>salePrice</b-th>
                    </b-tr>
                  </b-thead>
                  <b-tbody>
                    <b-tr>
                      <b-td stacked-heading="isbn" class="text-left">{{ row.item.isbn }}</b-td>
                      <b-td stacked-heading="title" class="text-left">{{ row.item.title }}</b-td>
                      <b-td stacked-heading="author" class="text-left">{{ row.item.author }}</b-td>
                      <b-td stacked-heading="description" class="text-left">{{ row.item.description }}</b-td>
                      <b-td stacked-heading="publisher" class="text-left">{{ row.item.publisher }}</b-td>
                      <b-td stacked-heading="publishDate" class="text-left">{{ row.item.publishDate }}</b-td>
                      <b-td stacked-heading="price" class="text-left">{{ row.item.price }}</b-td>
                      <b-td stacked-heading="salePrice" class="text-left">{{ row.item.salePrice }}</b-td>
                    </b-tr>
                  </b-tbody>
                </b-table-simple>
              </b-card-body>
            </b-col>
          </b-row>
        </b-card>
      </template>

    </b-table>
    <b-pagination
      v-model="page"
      :total-rows="total"
      :per-page="size"
      prev-text="Prev"
      next-text="Next"
      aria-controls="book-table"
      @input="searchPage()"
      align="center"
    ></b-pagination>
  </div>
</template>

<script>
export default {

  name: 'bookGrid',
  data () {
    return {
      fields: ['title', 'author', 'publisher', 'price', 'salePrice'],
      items: [],
      total: 10,
      page: 1,
      size: 10
    }
  },
  methods: {
    searchPage () {
      this.$EventBus.$emit('searchPage', this.page)
    },
    draw (data) {
      this.total = data.totalCount
      this.page = data.page
      this.size = data.size
      this.items = data.details.map(function (item, index, array) {
        item._showDetails = false
        if (item.salePrice === '-1') {
          item.salePrice = ''
        }
        return item
      })

      console.log(data)
    },
    expandAdditionalInfo (row) {
      row._showDetails = !row._showDetails
    }
  },
  mounted () {
    this.$EventBus.$on('drawGrid', this.draw)
  }
}
</script>

<style scoped>

</style>
