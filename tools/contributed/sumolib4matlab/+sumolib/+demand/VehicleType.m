classdef VehicleType
	properties %(GetAccess = private)
		id
        accel
		decel
		sigma
        tau
		vehTypelength
		minGap
		maxSpeed
        speedFactor
        speedDev
        color
		vClass
		emissionClass
		guiShape
		width
		imgFile
		impatience
		laneChangeModel
        personCapacity
        containerCapacity
        boardingDuration
        loadingDuration
    end
    methods
        function this = VehicleType(varargin)
            %sumolib.demand.VehicleType constructs a VehicleType object.
            %   myType = sumolib.demand.VehicleType(ID) creates an object
            %   myType identified by the string ID, that is an instance of
            %   the sumolib.demand.VehicleType class.
            %   Further properties can be defined as name-value pairs.
            %   These properties can be found at:
            %   http://sumo.dlr.de/wiki/Specification#Types
            
            %   Copyright 2015 Universidad Nacional de Colombia,
            %   Politecnico Jaime Isaza Cadavid.
            %   $Id$
            
            vClasses = {'ignoring','private','emergency','authority',...
                'army','vip','passenger','hov','taxi','bus','coach',...
                'delivery','truck','trailer','tram','rail_urban',...
                'rail','rail_electric','motorcycle','moped','bycicle',...
                'pedestrian','evehicle','ship','custom1','custom2'};
            
            guiShapes = {'pedestrian','bycicle','motorcycle','passenger',...
                'passenger/sedan','passenger/hatchback','passenger/wagon',...
                'passenger/van','delivery','truck','truck/semitrailer',...
                'truck/trailer','bus','bus/city','bus/flexible',...
                'bus/overland','rail','rail/light','rail/city','rail/slow',...
                'rail/fast','rail/cargo','evehicle','ship'};
            
			p = inputParser;
			p.FunctionName = 'sumolib.demand.VehicleType';
			p.addRequired('id', @ischar);
			p.addOptional('accel', [], @isnumeric);
			p.addOptional('decel', [], @isnumeric);
			p.addOptional('sigma', [], @isnumeric);
            p.addOptional('tau', [], @isnumeric);
            p.addOptional('length', [], @isnumeric);
            p.addOptional('minGap', [], @isnumeric);
            p.addOptional('maxSpeed', [], @isnumeric);
            p.addOptional('speedFactor', [], @isnumeric);
            p.addOptional('speedDev', [], @isnumeric);
            p.addOptional('color', [], @(x) length(x)<=3 && all(x)>=0 && all(x)<=1);
            p.addOptional('vClass', [], @(x) any(validatestring(x,vClasses)));
			p.addOptional('emissionClass', [], @ischar);
            p.addOptional('guiShape', [], @(x) any(validatestring(x,guiShapes)));
            p.addOptional('width', [], @isnumeric);
            p.addOptional('imgFile', [], @ischar);
            p.addOptional('impatience', [], @(x) isnumeric(x) || strcmp(x,'off') == 1);
            p.addOptional('laneChangeModel', [], @ischar);
            p.addOptional('personCapacity', [], @isnumeric);
            p.addOptional('containerCapacity', [], @isnumeric);
            p.addOptional('boardingDuration', [], @isnumeric);
            p.addOptional('loadingDuration', [], @isnumeric);
			p.parse(varargin{:})
			
			this.id = p.Results.id;
			this.accel = p.Results.accel;
			this.decel = p.Results.decel;
			this.sigma = p.Results.sigma;
			this.tau = p.Results.tau;
			this.vehTypelength = p.Results.length;
			this.minGap = p.Results.minGap;
			this.maxSpeed = p.Results.maxSpeed;
			this.speedFactor = p.Results.speedFactor;
            this.speedDev = p.Results.speedDev;
            this.color = num2str(p.Results.color);
            this.color = strrep(this.color, '  ', ',');
			this.vClass = p.Results.vClass;
			this.emissionClass = p.Results.emissionClass;
			this.guiShape = p.Results.guiShape;
			this.width = p.Results.width;
			this.imgFile = p.Results.imgFile;
            this.impatience = p.Results.impatience;
            this.laneChangeModel = p.Results.laneChangeModel;
            this.personCapacity = p.Results.personCapacity;
            this.containerCapacity = p.Results.containerCapacity;
            this.boardingDuration = p.Results.boardingDuration;
            this.loadingDuration = p.Results.loadingDuration;
        end
	end
end