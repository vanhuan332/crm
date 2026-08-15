import type { CompanyType } from '../api/companies'

export const companyTypeOptions: { value: CompanyType, label: string }[] = [
  { value: 'TRADITIONAL', label: 'Truyền thống' },
  { value: 'IT_SOLUTION', label: 'Giải pháp CNTT' },
  { value: 'IT_PRODUCT', label: 'Sản phẩm CNTT' },
  { value: 'TECH_STARTUP', label: 'Khởi nghiệp công nghệ' },
  { value: 'OTHER_ITO', label: 'ITO khác' }
]

const labels = Object.fromEntries(companyTypeOptions.map(option => [option.value, option.label])) as Record<CompanyType, string>

const tagClasses: Record<CompanyType, string> = {
  TRADITIONAL: 'tag--traditional',
  IT_SOLUTION: 'tag--it-solution',
  IT_PRODUCT: 'tag--it-product',
  TECH_STARTUP: 'tag--tech-startup',
  OTHER_ITO: 'tag--other-ito'
}

export const companyTypeLabel = (type: CompanyType) => labels[type]
export const companyTypeTagClass = (type: CompanyType) => tagClasses[type]
