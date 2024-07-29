<template>
  <div class='dashboard-container'>
    <div class='container'>
      <div class='tableBar'>
        <label style='margin-right: 5px'>员工姓名：</label>
        <el-input
          v-model='input'
          clearable
          placeholder='请输入员工姓名'
          style='width: 15%'
          @clear='init'
          @keyup.enter.native='initFun'
        />
        <el-button class='normal-btn continue' @click='init(true)'
        >查询
        </el-button
        >
        <el-button
          style='float: right'
          type='primary'
          @click="addEmployeeHandle('add')"
        >
          + 添加员工
        </el-button>
      </div>
      <el-table
        v-if='tableData.length'
        :data='tableData'
        class='tableBox'
        stripe
      >
        <el-table-column label='员工姓名' prop='name' />
        <el-table-column label='账号' prop='username' />
        <el-table-column label='手机号' prop='phone' />
        <el-table-column label='账号状态'>
          <template slot-scope='scope'>
            <div
              :class="{ 'stop-use': String(scope.row.status) === '0' }"
              class='tableColumn-status'
            >
              {{ String(scope.row.status) === '0' ? '禁用' : '启用' }}
            </div>
          </template>
        </el-table-column>
        <el-table-column label='最后操作时间' prop='updateTime' />
        <el-table-column align='center' label='操作' width='160'>
          <template slot-scope='scope'>
            <el-button
              :class="{ 'disabled-text': scope.row.username === 'admin' }"
              :disabled="scope.row.username === 'admin'"
              class='blueBug'
              size='small'
              type='text'
              @click='addEmployeeHandle(scope.row.id, scope.row.username)'
            >
              修改
            </el-button>
            <el-button
              :class="{
                'disabled-text': scope.row.username === 'admin',
                blueBug: scope.row.status == '0',
                delBut: scope.row.status != '0',
              }"
              :disabled="scope.row.username === 'admin'"
              class='non'
              size='small'
              type='text'
              @click='statusHandle(scope.row)'
            >
              {{ scope.row.status == '1' ? '禁用' : '启用' }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <Empty v-else :is-search='isSearch' />
      <el-pagination
        :page-size='pageSize'
        :page-sizes='[10, 20, 30, 40]'
        :total='counts'
        class='pageList'
        layout='total, sizes, prev, pager, next, jumper'
        @size-change='handleSizeChange'
        @current-change='handleCurrentChange'
      />
    </div>
  </div>
</template>

<script lang='ts'>
import { Component, Vue } from 'vue-property-decorator'
import HeadLable from '@/components/HeadLable/index.vue'
import { getEmployeeList, enableOrDisableEmployee } from '@/api/employee'
import { UserModule } from '@/store/modules/user'
import InputAutoComplete from '@/components/InputAutoComplete/index.vue'
import Empty from '@/components/Empty/index.vue'

@Component({
  name: 'Employee',
  components: {
    HeadLable,
    InputAutoComplete,
    Empty
  }
})
export default class extends Vue {
  private input: any = ''
  private counts: number = 0
  private page: number = 1
  private pageSize: number = 10
  private tableData = []
  private id = ''
  private status = ''
  private isSearch: boolean = false

  get userName() {
    return UserModule.username
  }

  created() {
    this.init()
  }

  initProp(val) {
    this.input = val
    this.initFun()
  }

  initFun() {
    this.page = 1
    this.init()
  }

  private async init(isSearch?: boolean) {
    this.isSearch = isSearch
    const params = {
      page: this.page,
      pageSize: this.pageSize,
      name: this.input ? this.input : undefined
    }
    await getEmployeeList(params)
      .then((res: any) => {
        if (String(res.data.code) === '1') {
          this.tableData = res.data && res.data.data && res.data.data.records
          this.counts = res.data.data.total
        }
        // if (!res.data.data.records.length && type === 'search') {
        //   this.$message.error('未搜索到相关员工，请核对员工姓名是否正确')
        // }
      })
      .catch((err) => {
        this.$message.error('请求出错了：' + err.message)
      })
  }

  // 添加
  private addEmployeeHandle(st: string, username: string) {
    if (st === 'add') {
      this.$router.push({ path: '/employee/add' })
    } else {
      if (username === 'admin') {
        return
      }
      this.$router.push({ path: '/employee/add', query: { id: st } })
    }
  }

  //状态修改
  private statusHandle(row: any) {
    if (row.username === 'admin') {
      return
    }
    this.id = row.id
    this.status = row.status
    this.$confirm('确认调整该账号的状态?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      enableOrDisableEmployee({ id: this.id, status: !this.status ? 1 : 0 })
        .then((res) => {
          if (String(res.status) === '200') {
            this.$message.success('账号状态更改成功！')
            this.init()
          }
        })
        .catch((err) => {
          this.$message.error('请求出错了：' + err.message)
        })
    })
  }

  private handleSizeChange(val: any) {
    this.pageSize = val
    this.init()
  }

  private handleCurrentChange(val: any) {
    this.page = val
    this.init()
  }
}
</script>

<style lang='scss' scoped>
.disabled-text {
  color: #bac0cd !important;
}
</style>
