@description('Project name')
@minLength(3)
@maxLength(22)
param projectName string

@description('Deployment Location')
@allowed(['westeurope', 'norteurope', 'centralus'])
param location string

@allowed(['Standard_LRS', 'Standard_GRS'])
param sku string = 'Standard_LRS'

param resourceTags object = { environment: 'staging' }

var storageAccountName = toLower('st${projectName}')

resource st 'Microsoft.Storage/storageAccounts@2022-09-01' = {
  name: storageAccountName
  location: location
  sku: { name: sku }
  kind: 'StorageV2'
  properties: { accessTier: 'Hot' }
  tags: resourceTags
}

output storageAccountId string = st.id
output storageAccountName string = st.name
