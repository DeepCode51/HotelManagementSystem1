<template>
  <el-card class="box-card" shadow="always">
    <div slot="header">
      <span>房间物品管理</span>
      <el-input v-model="searchData" placeholder="请输入物品名称">
        <el-button slot="append" type="primary" icon="el-icon-search" @click="search">搜索</el-button>
      </el-input>
    </div>
    <el-table
      ref="multipleTable"
      :data="list"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"/>
      <el-table-column
        prop="id"
        label="编号"
        width="50"/>
      <el-table-column
        prop="name"
        label="名称"/>
      <el-table-column
        prop="remark"
        label="备注"/>
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
            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-popover
            v-model="scope.row.visible2"
            placement="top"
            width="160">
            <p>确定删除吗？</p>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" round @click="scope.row.visible2 = false">取消</el-button>
              <el-button type="danger" size="mini" round @click="handleDel(scope.row)">确定</el-button>
            </div>
            <el-button slot="reference" :loading="scope.row.loading" size="mini" type="danger" @click="scope.row.visible2 = true">删除</el-button>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 14px;">
      <div class="bottom">
        <el-button type="primary" @click="navigateTo('add')">添加房间物品信息</el-button>
      </div>
    </div>
  </el-card>
</template>

<script>
import { getAllRoomGoods, delRoomGoods } from '../../api/roomGoods'

export default {
  filters: {
  },
  data() {
    return {
      visible2: false,
      multipleSelection: [],
      list: null,
      listLoading: true,
      searchData: ''
    }
  },
  created: function() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      getAllRoomGoods(this.searchData).then(response => {
        this.list = response.data
        this.listLoading = false
      })
    },
    search() {
      this.fetchData()
    },
    navigateTo(val) {
      this.$router.push({ path: '/roomGoods/' + val })
    },
    handleEdit(index, row) {
      this.$router.push({
        path: '/roomGoods/edit',
        name: 'EditRoomGoods',
        params: {
          id: row.id
        }
      })
    },
    handleDel(row) {
      row.visible2 = false
      row.loading = true
      delRoomGoods(row.id).then(response => {
        const res = response
        if (res.code === 1000) {
          this.$message({
            message: '删除成功！',
            type: 'success'
          })
          this.list.push()
          row.loading = false
          this.fetchData()
        } else {
          this.$message({
            message: '删除失败！',
            type: 'error'
          })
        }
      }).catch(error => {
        row.loading = false
        console.log(error)
      })
      row.loading = false
    },
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    showStatus(val) {
      switch (val) {
        case 0: return 'info'
        case 1: return 'success'
        case 2: return 'warning'
        case 3: return 'danger'
      }
    }
  }
}
</script>

<style>
  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }
</style>
