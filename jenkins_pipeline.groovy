pipeline
{
    agent any
    // agent { label: 'Windows' }

		parameters
		{
				string(name: 'resourceGroupName', defaultValue: '-rg')
				string(name: 'resourceGroupLocation', defaultValue: 'centralus')
		}

    environment
    {
				// Don't do this!!
        azuser=""
        azpwd=""
    }
    stages
    {
        stage('Clone Repository')
        {
            steps
            {
                script
                {
                    git 'https://github.com/mvare007/az-bicep-examples.git'
                }
            }
        }
        stage('Azure Login')
        {
            steps
            {
                script
                {
                    bat label: '', script: 'az login -u ' + azuser + ' -p ' + azpwd
                }
            }
        }
        stage('Create ARM deployment')
        {
            steps
            {
                script
                {
                    bat label: '', script: 'az deployment sub create --name RGDeployment --location centralus --template-file ./resourcegroup.bicep --parameters resourceGroupName=' + params.resourceGroupName + ' resourceGroupLocation=' + params.resourceGroupLocation
                }
            }
        }
				stage('Create Storage Account')
				{
					steps
					{
						script
						{
							bat label:'', script: 'az deployment group create -g ' + params.resourcegroup + ' --template-file ./accountstorage.bicep  --parameters ./accountstorage.parameters.json'
						}
					}
				}
    }
}