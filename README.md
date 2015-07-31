Command Line Binary

Purpose:
  Create Job 

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
    -jobname:        [MANDATORY] Name of Job to create
	 -template:       [MANDATORY] Template for job creation (ex: JOBS.WIN, JOBS.UNX)
	 -folder:         [MANDATORY] Folder in which to create the object
	 -login:          [OPTIONAL] Login for Job
	 -host:           [OPTIONAL] Host for Job
	 -process:        [OPTIONAL] Content of process tab
	 -title:          [OPTIONAL] Title of Job object
	 -queue:          [OPTIONAL] Queue of job object
	 -timezone:       [OPTIONAL] Timezone of job object
	 -priority:       [OPTIONAL] Job priority
	 -genatruntime    [OPTIONAL] Generate At Runtime
	 -maxparallelrun: [OPTIONAL] Max Parallel Run
	 -inactive        [OPTIONAL] Active or Inactive Job
	 -preprocess:     [OPTIONAL] Content of preprocess tab
	 -postprocess:    [OPTIONAL] Content of postprocess tab
