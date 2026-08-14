import { cleanup, fireEvent, render, screen } from '@testing-library/vue'
import { nextTick } from 'vue'
import { afterEach, describe, expect, it, vi } from 'vitest'
import CompanyDeleteDialog from '../CompanyDeleteDialog.vue'

describe('CompanyDeleteDialog', () => {
  afterEach(cleanup)
  it('calls delete only after explicit confirmation', async () => {
    const mockDeleteCompany = vi.fn()

    render(CompanyDeleteDialog, {
      props: { companyName: 'ABC Technology', onConfirm: mockDeleteCompany }
    })

    expect(mockDeleteCompany).not.toHaveBeenCalled()
    await fireEvent.click(screen.getByRole('button', { name: 'Xóa công ty' }))
    expect(mockDeleteCompany).toHaveBeenCalledTimes(1)
  })

  it('keeps keyboard focus inside the dialog', async () => {
    render(CompanyDeleteDialog, { props: { companyName: 'ABC Technology' } })

    await nextTick()
    const cancel = screen.getByRole('button', { name: 'Hủy' })
    const confirm = screen.getByRole('button', { name: 'Xóa công ty' })
    expect(document.activeElement).toBe(cancel)

    confirm.focus()
    await fireEvent.keyDown(confirm, { key: 'Tab' })
    expect(document.activeElement).toBe(cancel)
  })
})
