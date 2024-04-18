pipeline
{
    agent any
    // agent { label: 'Windows' }

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
                    properties([

                        parameters([
                            stringParam(
                                name: 'resourceGroupName',
                                default: '-rg'
                                ),
                            stringParam(
                                name: 'resourceGroupLocation',
                                default: 'centralus'
                                )
                        ])
                    ])

                    bat label: '', script: 'az deployment sub create --name RGDeployment --location centralus --template-file ./resourcegroup.bicep --parameters resourceGroupName=' + params.resourceGroupName + ' resourceGroupLocation=' + params.resourceGroupLocation
                }
            }
        }
    }
}