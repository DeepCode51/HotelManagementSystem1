<template>
  <el-card class="box-card" shadow="always">
    <div slot="header">
      <span>入住登记</span>
      <el-input v-model="searchData" placeholder="请输入预订方式/房间类型">
        <el-button slot="append" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
      </el-input>
    </div>
    <el-table
      ref="multipleTable"
      :loading="listLoading"
      :data="list.slice((currentPage-1)*pagesize,currentPage*pagesize)"
      tooltip-effect="dark"
      style="width: 100%">
      <el-table-column
        type="selection"
        width="55"/>
      <el-table-column
        prop="orderId"
        label="编号"/>
      <el-table-column
        prop="orderType"
        label="预订方式"/>
      <el-table-column
        prop="roomType"
        label="房间类型"/>
      <el-table-column
        prop="name"
        label="预订人姓名"/>
      <el-table-column
        prop="phone"
        label="预留手机号"/>
      <el-table-column
        prop="orderDate"
        label="预订日期">
        <template slot-scope="scope">
          <span>{{ scope.row.orderDate | formatDate }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="orderDays"
        label="预定天数"/>
      <el-table-column
        label="订单状态">
        <template slot-scope="scope">
          {{ scope.row.orderStatus | getOrderStatus }}
        </template>
      </el-table-column>
      <el-table-column
        sortable
        label="订单消费">
        <template slot-scope="scope">
          ￥ {{ scope.row.orderCost }}
        </template>
      </el-table-column>
      <el-table-column
        label="修改时间|创建时间">
        <template slot-scope="scope">
          <i class="el-icon-time"/>
          <span style="margin-left: 10px">{{ scope.row.updateTime | formatDate }}</span>
          <br>
          <i class="el-icon-time"/>
          <span style="margin-left: 10px">{{ scope.row.createTime | formatDate }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="doCheckIn(scope.row)">入住</el-button>
        </template>
      </el-table-column>

    </el-table>

    <div style="padding: 14px;">
      <el-pagination

        :current-page="currentPage"
        :page-size="pagesize"
        :total="list.length"
        style="float: right"
        background
        layout="total, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"/>

    </div>

  </el-card>
</template>

<script>
import { getAllOrder } from '../../api/order'
import { checkIn } from '../../api/checkIn'

export default {
  name: 'CheckIn',
  data() {
    return {
      currentPage: 1,
      pagesize: 10,
      list: [{}, {}, {}, {}],
      visible2: false,
      loading: null,
      listLoading: false,
      multipleSelection: null,
      searchData: '',
      status: '1' //只查已付款的订单
    }
  },
  created: function() {
    this.fetchData()
  },
  methods: {
    handleSizeChange(val) {
      this.pagesize = val
    },
    handleCurrentChange(val) {
      this.currentPage = val
    },
    search() {
      this.fetchData()
    },
    fetchData() {
      getAllOrder(this.searchData,this.status).then(response => {
        this.list = response.data
        this.list.reverse()
      }).catch(err => {
        this.$notify.error({
          title: '错误',
          message: err.toString()
        })
      })
    },
    doCheckIn(row){
      checkIn({ orderId: row.orderId}).then(res => {
        if (res.code !== 1000) {
          this.$message.warning(res.message)
        } else {
          this.$message.success('入住成功')
          this.fetchData()
        }
      })
    },
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    }
  }
}
</script>

<style scoped>

</style>
