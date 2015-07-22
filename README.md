Command Line Binary

Purpose:
  Delete Objects (of any ty

Parameters:
  Generic Parameters for AE Connection (all of them can be specified in connection.config file directly):
  
    -H: Hostname for Automation Engine
    -P: Primary CP Port (2217 by default)
    -L: Login to Automation Engine
    -D: Department
    -W: Password
    -C: Client
    -help: Show the list of Generic Options
    
  Module Specific Parameters:
  
    -h: Show the list of Specific Parameters
    -jobname: MANDATORY - Name of Job to create
		-template: MANDATORY - Template for job creation (ex: JOBS.WIN, JOBS.UNX)
		-folder: MANDATORY - Folder in which to create the object
		-login: Login for Job
		-host: Host for Job
		-process: Content of process tab
		-title: Title of Job object
		-queue: Queue of job object
		-timezone: Timezone of job object
		-priority: Job priority
		-genatruntime: Generate At Runtime
		-maxparallelrun: Max Parallel Run
		-inactive: Active or Inactive Job
		-preprocess: Content of preprocess tab
		-postprocess: Content of postprocess tab
