import { render, screen } from '@testing-library/vue'
import { flushPromises } from '@vue/test-utils'
import { AxiosError, type AxiosResponse } from 'axios'
import { createPinia, setActivePinia } from 'pinia'
import { beforeEach, describe, expect, it, vi } from 'vitest'
import CompanyDetailView from '../CompanyDetailView.vue'
import CompaniesListView from '../CompaniesListView.vue'
import { getCompany, listCompanies } from '../../../api/companies'
import router from '../../../router'

vi.mock('../../../api/companies', () => ({
  listCompanies: vi.fn(),
  getCompany: vi.fn()
}))

const mockListCompanies = vi.mocked(listCompanies)
const mockGetCompany = vi.mocked(getCompany)

const company = {
  id: 1,
  name: 'ABC Technology',
  industry: 'Software',
  companyType: 'IT_SOLUTION' as const,
  country: 'Vietnam',
  website: 'https://abc.example',
  phone: '0123456789',
  address: 'Hanoi',
  description: 'Technology consulting',
  createdAt: '2026-08-13T00:00:00Z',
  updatedAt: '2026-08-13T00:00:00Z',
  version: 0
}

describe('CompaniesListView', () => {
  let pinia: ReturnType<typeof createPinia>

  beforeEach(() => {
    pinia = createPinia()
    setActivePinia(pinia)
    mockListCompanies.mockReset()
    mockGetCompany.mockReset()
  })

  it('renders a loaded company and links to its detail page', async () => {
    mockListCompanies.mockResolvedValue([company])

    render(CompaniesListView, { global: { plugins: [pinia, router] } })

    await flushPromises()

    expect(screen.getByText('ABC Technology')).toBeTruthy()
    expect(screen.getByRole('link', { name: 'Xem' }).getAttribute('href')).toBe('/companies/1')
  })

  it('shows a retryable load error instead of not found when detail loading fails outside of 404', async () => {
    mockGetCompany.mockRejectedValue(new AxiosError(
      'Request failed',
      undefined,
      undefined,
      undefined,
      { status: 500 } as AxiosResponse
    ))

    render(CompanyDetailView, { props: { id: 1 }, global: { plugins: [pinia, router] } })

    await flushPromises()

    expect(screen.getByRole('heading', { name: 'Không thể tải thông tin công ty' })).toBeTruthy()
    expect(screen.getByRole('button', { name: 'Thử lại' })).toBeTruthy()
  })
})
