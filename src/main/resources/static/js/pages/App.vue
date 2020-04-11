<template>
    <v-app>
        <v-content>
            <v-container>
                <v-card>
                    <v-card-title>
                        <h1>Список покупок</h1>
                    </v-card-title>
                    <v-card-actions>
                        <v-row>
                            <v-col>
                                <v-text-field
                                        label="Продукт"
                                        outlined
                                        v-model="buyName"
                                        color="purple"
                                ></v-text-field>
                            </v-col>
                            <v-col>
                                <v-text-field
                                        label="Цена"
                                        outlined
                                        v-model="buyPrice"
                                        color="purple"
                                ></v-text-field>
                            </v-col>
                            <v-col>
                                <v-btn large block outlined color="green" @click="buyProduct" height="55" :disabled="buyName === ''">
                                    Добавить
                                </v-btn>
                            </v-col>
                        </v-row>
                    </v-card-actions>
                    <v-card-actions>
                        <v-row>
                            <v-col>
                                <v-simple-table>
                                    <thead>
                                    <tr>
                                        <th class="text-left">Предмет</th>
                                        <th class="text-left">Цена</th>
                                        <th class="text-left">Куплено</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr v-for="item in list" :key="item.name">
                                        <td class="font-weight-light title">{{ item.name }}</td>
                                        <td class="font-weight-regular title"><i>{{ item.price }}</i></td>
                                        <td>
                                            <v-checkbox @change="addID(item.productID)"></v-checkbox>
                                        </td>
                                    </tr>
                                    </tbody>
                                </v-simple-table>
                            </v-col>
                        </v-row>
                    </v-card-actions>

                    <v-divider/>
                    <v-card-text>
                        <v-row>
                            <v-col cols="6">
                                Итого
                            </v-col>
                            <v-col>
                                <span>Сумма:</span>&nbsp;<span class="font-weight-medium" style="color: black">{{totalPriceComp}}</span>
                            </v-col>
                            <v-col>
                                <span>Кол-во:</span>&nbsp;<span class="font-weight-medium" style="color: black">{{totalItemAmount}}</span>
                            </v-col>
                        </v-row>
                    </v-card-text>

                    <v-card-actions>
                        <v-row>
                            <v-col>
                                <v-btn block large color="#115377" outlined @click="removeProduct">
                                    Удалить купленное
                                </v-btn>
                            </v-col>
                        </v-row>
                    </v-card-actions>
                </v-card>
            </v-container>
        </v-content>
    </v-app>
</template>

<script>
    import axios from 'axios'
    export default {
        data() {
            return {
                list: [],
                buyName: '',
                buyPrice: '',
                completed:[],
                totalPrice: 0
            }
        },
        created() {
            axios.get('/getProducts').then(response => {
                this.list = response.data
            })
        },
        computed: {

            totalItemAmount() {
                let amount = 0
                this.list.forEach(() => amount++)
                return amount
            },

            totalPriceComp() {
                let total = 0
                this.list.forEach(value => total+=value.price)
                return total
            }

        },
        methods: {
            buyProduct() {
                const buyData = {
                    'name': this.buyName,
                    'price': this.buyPrice
                }
                axios.post('/buyProduct', buyData).then(response => {
                    this.list = response.data
                })
                this.buyName = ''
                this.buyPrice = ''
            },
            addID(id) {
                this.completed.push(id)
            },
            removeProduct() {
                axios.post('/completeProducts', this.completed).then(response => {
                    this.list = response.data
                    this.completed = []
                })
            }
        }
    }
</script>

<style scoped></style>