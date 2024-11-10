<template>
    <el-card class="box-card" shadow="always" style="height: 100%;">
        <div id="liveRate" style="left:0;width: 100%;height: 500px" />
    </el-card>
</template>
  
<script>
import { roomAccommodationDtatistics } from '@/api/roomType'

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
            var rtChart = this.$echarts.init(document.getElementById('liveRate'), 'light')
            roomAccommodationDtatistics().then(res => {

                rtChart.setOption({
                    title: {
                        text: '客房住宿率',
                        left: 'center'
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
                            formatter: '{value} %'
                        }
                    },
                    series: [{
                        data: res.data.checkinNum,
                        type: 'line'
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
  