<template>
  <el-card class="app-container" shadow="always">
    <div slot="header">
      <span>编辑物品信息</span>
    </div>
    <el-form ref="form1" :model="form1" >
      <el-form-item
        :rules="[
          { required: true, message: '不能为空'}
        ]"
        label="物品名称"
        prop="name"
      >
        <el-input v-model.number="form1.name" placeholder="请输物品名称"/>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form1.remark" type="textarea"/>
      </el-form-item>
      <el-form-item>
        <el-button :loading="loading" type="primary" @click="onSubmit()">提交</el-button>
        <el-button @click="onCancel">取消</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
import { editRoomGoods, getById } from '../../api/roomGoods'
import ElCard from 'element-ui/packages/card/src/main'
export default {
  components: { ElCard },
  data() {
    return {
      form1: {
        name: null,
        remark: null
      },
      loading: false,
      typeList: null,
      statusList: {
        unavailable: 0,
        available: 1,
        occupied: 2,
        inUse: 3
      }
    }
  },
  created: function() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.roomId = this.$route.params.id
      if (this.roomId == null) {
        this.onCancel()
      }
      getById(this.roomId).then(res => {
        this.form1 = res.data
      })
    },
    idToType(val) {
      if (val == null) return
      this.typeList.forEach(type => {
        if (val === type.typeId) {
          this.form1.roomType = type.roomType
        }
      })
    },
    onSubmit() {
      this.$refs.form1.validate((valid) => {
        if (valid) {
          this.loading = true
          editRoomGoods(this.form1).then(response => {
            const res = response
            if (res.code === 1000) {
              this.$message({
                message: '提交成功！',
                type: 'success'
              })
              this.loading = false
              setTimeout(this.onCancel(), 20000)
            } else {
              this.showError(res.message)
              this.loading = false
            }
          })
        } else {
          this.loading = false
          return false
        }
      })
    },
    showError(msg) {
      this.$message({
        message: msg,
        type: 'error'
      })
    },
    onCancel() {
      this.$router.back()
    }
  }
}
</script>

<style scoped>
</style>
