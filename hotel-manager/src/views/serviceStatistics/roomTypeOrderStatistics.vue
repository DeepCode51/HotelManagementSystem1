<template>
    <el-card class="box-card" shadow="always" style="height: 100%;">
        <div id="roomOrderType" style="left:0;width: 100%;height: 500px" />
    </el-card>
</template>
  
<script>
import { roomTypeOrderStatistics } from '@/api/roomType'

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
            var rtChart = this.$echarts.init(document.getElementById('roomOrderType'), 'light')
            roomTypeOrderStatistics().then(res => {
                let option = {
                    title: {
                        text: '各类房型订单统计',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: (params ) => {
                            console.log(params)
                            const str = `${params.seriesName}<br/>${params.data.name}：${params.data.value}${params.data.unit}`
                            return str
                        },
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left'
                    },
                    series: [
                        {
                            name: '各类房型订单比例',
                            type: 'pie',
                            radius: '50%',
                            data: res.data,
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                rtChart.setOption(option);

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
  