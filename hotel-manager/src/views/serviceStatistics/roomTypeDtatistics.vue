<template>
    <el-card class="box-card" shadow="always" style="height: 100%;">
        <div id="roomType" style="left:0;width: 100%;height: 500px" />
    </el-card>
</template>
  
<script>
import { roomTypeDtatistics } from '@/api/roomType'

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
            var rtChart = this.$echarts.init(document.getElementById('roomType'), 'light')
            roomTypeDtatistics().then(res => {
                let option = {
                    title: {
                        text: '房型统计',
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
                            name: '房型比例',
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
                // console.log(res)
                // rtChart.setOption({
                //     tooltip: {},
                //     series: [{
                //         name: '比例',
                //         type: 'pie',
                //         radius: '55%',
                //         data: [
                //             { value: 35, name: '单人房' },
                //             { value: 55, name: '大床房' },
                //             { value: 45, name: '双床房' },
                //             { value: 55, name: '商务大床房' },
                //             { value: 45, name: '商务双床房' },
                //             { value: 35, name: '豪华大床房' }
                //         ]
                //     }]
                // })
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
  