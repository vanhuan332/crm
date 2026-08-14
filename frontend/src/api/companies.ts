import { http } from './http'

export type CompanyType = 'TRADITIONAL' | 'IT_SOLUTION' | 'IT_PRODUCT' | 'TECH_STARTUP' | 'OTHER_ITO'

export interface Company {
  id: number
  name: string
  industry: string
  companyType: CompanyType
  country: string | null
  website: string | null
  phone: string | null
  address: string | null
  description: string | null
  createdAt: string
  updatedAt: string
  version: number
}

export type CompanyInput = Omit<Company, 'id' | 'createdAt' | 'updatedAt'>

export const listCompanies = () => http.get<Company[]>('/companies').then(response => response.data)

export const getCompany = (id: number) => http.get<Company>(`/companies/${id}`).then(response => response.data)

export const createCompany = (input: CompanyInput) => http.post<Company>('/companies', input).then(response => response.data)

export const updateCompany = (id: number, input: CompanyInput) => http.put<Company>(`/companies/${id}`, input).then(response => response.data)

export const deleteCompany = (id: number) => http.delete(`/companies/${id}`)
