import { defineStore } from 'pinia'
import { isAxiosError } from 'axios'
import { createCompany, deleteCompany, getCompany, listCompanies, updateCompany, type Company, type CompanyInput } from '../api/companies'

type LoadStatus = 'idle' | 'loading' | 'error' | 'notFound'

export const useCompaniesStore = defineStore('companies', {
  state: () => ({
    companies: [] as Company[],
    listStatus: 'idle' as LoadStatus,
    listError: '',
    company: null as Company | null,
    detailStatus: 'idle' as LoadStatus,
    detailError: ''
  }),
  actions: {
    async loadCompanies() {
      this.listStatus = 'loading'
      this.listError = ''
      try {
        this.companies = await listCompanies()
        this.listStatus = 'idle'
      } catch {
        this.listStatus = 'error'
        this.listError = 'Không thể tải danh sách công ty.'
      }
    },
    async loadCompany(id: number) {
      this.detailStatus = 'loading'
      this.detailError = ''
      this.company = null
      try {
        this.company = await getCompany(id)
        this.detailStatus = 'idle'
      } catch (error) {
        if (isAxiosError(error) && error.response?.status === 404) {
          this.detailStatus = 'notFound'
          this.detailError = 'Không tìm thấy công ty.'
        } else {
          this.detailStatus = 'error'
          this.detailError = 'Không thể tải thông tin công ty.'
        }
      }
    },
    async createCompany(input: CompanyInput) {
      const company = await createCompany(input)
      this.companies = [...this.companies, company]
      return company
    },
    async updateCompany(id: number, input: CompanyInput) {
      const company = await updateCompany(id, input)
      this.company = company
      this.companies = this.companies.map(item => item.id === id ? company : item)
      return company
    },
    async deleteCompany(id: number) {
      await deleteCompany(id)
      this.companies = this.companies.filter(item => item.id !== id)
      if (this.company?.id === id) this.company = null
    }
  }
})
