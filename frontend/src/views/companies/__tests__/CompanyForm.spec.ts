import { cleanup, fireEvent, render, screen } from '@testing-library/vue'
import { afterEach, describe, expect, it } from 'vitest'
import CompanyForm from '../CompanyForm.vue'

describe('CompanyForm', () => {
  afterEach(cleanup)

  it('does not submit blank name and keeps entered industry', async () => {
    render(CompanyForm)

    await fireEvent.update(screen.getByLabelText(/^Ngành/), 'Software')
    await fireEvent.click(screen.getByRole('button', { name: 'Lưu' }))

    expect(screen.getByText('Tên công ty là bắt buộc')).toBeTruthy()
    expect((screen.getByLabelText(/^Ngành/) as HTMLInputElement).value).toBe('Software')
  })

  it('emits the form input when client validation succeeds', async () => {
    const { emitted } = render(CompanyForm)

    await fireEvent.update(screen.getByLabelText(/^Tên công ty/), 'ABC Technology')
    await fireEvent.update(screen.getByLabelText(/^Ngành/), 'Software')
    await fireEvent.click(screen.getByRole('button', { name: 'Lưu' }))

    expect(emitted().submit?.[0]).toEqual([expect.objectContaining({
      name: 'ABC Technology',
      industry: 'Software',
      companyType: 'TRADITIONAL',
      version: 0
    })])
  })

  it('maps server field errors to every submitted control', () => {
    render(CompanyForm, {
      props: {
        fieldErrors: {
          companyType: 'Loại công ty không hợp lệ', country: 'Quốc gia không hợp lệ',
          website: 'Website không hợp lệ', phone: 'Điện thoại không hợp lệ',
          address: 'Địa chỉ không hợp lệ', description: 'Mô tả không hợp lệ', version: 'Dữ liệu đã thay đổi'
        }
      }
    })

    expect(screen.getByLabelText(/^Loại công ty/).getAttribute('aria-describedby')).toBe('company-type-error')
    expect(screen.getByLabelText('Quốc gia').getAttribute('aria-describedby')).toBe('company-country-error')
    expect(screen.getByLabelText('Website').getAttribute('aria-describedby')).toBe('company-website-error')
    expect(screen.getByLabelText('Điện thoại').getAttribute('aria-describedby')).toBe('company-phone-error')
    expect(screen.getByLabelText('Địa chỉ').getAttribute('aria-describedby')).toBe('company-address-error')
    expect(screen.getByLabelText('Mô tả').getAttribute('aria-describedby')).toBe('company-description-error')
    expect(screen.getByText('Dữ liệu đã thay đổi')).toBeTruthy()
  })
})
