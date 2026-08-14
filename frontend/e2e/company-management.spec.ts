import { expect, test } from '@playwright/test'

test('Sales completes create, detail, update and soft delete', async ({ page }) => {
  const companyName = `ABC Technology ${Date.now()}`
  const updatedCompanyName = `${companyName} Updated`

  await page.goto('/companies/new')
  await page.getByLabel('Tên công ty').fill(companyName)
  await page.getByLabel('Ngành').fill('Software')
  await page.getByLabel('Loại công ty').selectOption('IT_SOLUTION')
  await page.getByRole('button', { name: 'Lưu' }).click()

  await expect(page).toHaveURL(/\/companies\/\d+$/)
  await expect(page.getByRole('heading', { name: companyName })).toBeVisible()
  await expect(page.getByText('Software', { exact: true })).toBeVisible()

  await page.getByRole('link', { name: 'Chỉnh sửa' }).click()
  await expect(page).toHaveURL(/\/companies\/\d+\/edit$/)
  await page.getByLabel('Tên công ty').fill(updatedCompanyName)
  await page.getByRole('button', { name: 'Lưu' }).click()

  await expect(page).toHaveURL(/\/companies\/\d+$/)
  await expect(page.getByRole('heading', { name: updatedCompanyName })).toBeVisible()

  await page.getByRole('button', { name: 'Xóa công ty' }).click()
  await page.getByRole('dialog').getByRole('button', { name: 'Xóa công ty' }).click()
  await expect(page).toHaveURL('/companies')
  await expect(page.getByText(updatedCompanyName, { exact: true })).toHaveCount(0)
})
