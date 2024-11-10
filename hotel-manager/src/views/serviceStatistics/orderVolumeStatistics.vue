<template>
    <el-card class="box-card" shadow="always" style="height: 100%;">
        <div id="order" style="left:0;width: 100%;height: 500px" />
    </el-card>
</template>
  
<script>
import { orderVolumeStatistics } from '@/api/roomType'

export default {

    data() {
        return {
            visible2: false,
            multipleSelection: [],
            listLoading: true,
            list: null,
            loading: false,
            searchData: ''
        }
    },
    mounted: function () {
        this.rtChart()
    },
    methods: {
        rtChart() {
            var rtChart = this.$echarts.init(document.getElementById('order'), 'light')
            orderVolumeStatistics().then(res => {

                rtChart.setOption({
                    title: {
                        text: '订单统计',
                        left: '1%'
                    },
                    itemStyle: {
                        color: '#409EFF'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: { // 坐标轴指示器，坐标轴触发有效
                            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    xAxis: {
                        type: 'category',
                        data: res.data.days,
                        axisLabel: {
                            formatter: '{value} 月'
                        }
                    },
                    yAxis: {
                        type: 'value',
                        axisLabel: {
                            formatter: '{value} 单'
                        }
                    },
                    series: [{
                        name: '订单量',
                        data: res.data.orderNum,
                        type: 'bar'
                    }]
                })
                

            })

        }
    }
}
</script>
  
<style>
.red {
    color: red;
}

.bottom {
    margin-top: 13px;
    line-height: 12px;
}
</style>
  