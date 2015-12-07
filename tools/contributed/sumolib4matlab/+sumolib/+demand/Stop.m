classdef Stop
	properties %(GetAccess = private)
		busStop
        lane
        endPos
		startPos
        friendlyPos
		duration
		until
		index
        triggered
        containerTriggered
        parking
        actType
    end
    methods
		function this = Stop(varargin)
            %sumolib.demand.Stop constructs a Stop object.
            %   myStop = sumolib.demand.Stop(BUSSTOP) creates an
            %   object myStop in the bus stops defined by BUSSTOP. BUSSTOP
            %   is a cell array containing objects of the class
            %   sumolib.demand.BusStop. The duration of the stop is given
            %   by the SUMO default values.
            %   myStop = sumolib.demand.Stop(LANE) creates an
            %   object myStop in the lane with id LANE.
            %   Further properties can be defined as name-value pairs.
            %   These properties can be found at:
            %   http://sumo.dlr.de/wiki/Specification#Stops
            
            %   Copyright 2015 Universidad Nacional de Colombia,
            %   Politecnico Jaime Isaza Cadavid.
            %   $Id$
            
			p = inputParser;
			p.FunctionName = 'sumolib.demand.Stop';
			p.addRequired('busStopOrLane', @(x) iscell(x) || ischar(x));
			p.addOptional('endPos', [], @isnumeric);
			p.addOptional('startPos', [], @isnumeric);
            p.addOptional('friendlyPos', [], @isbool);
            p.addOptional('duration', [], @isnumeric);
            p.addOptional('until', [], @isnumeric);
            p.addOptional('index', [], @isnumeric);
            p.addOptional('triggered', [], @isbool);
            p.addOptional('containerTriggered', [], @isbool);
            p.addOptional('parking', [], @isbool);
            p.addOptional('actType', [], @ischar);
			p.parse(varargin{:})

            if iscell(p.Results.busStopOrLane)
                this.busStop = p.Results.busStopOrLane;
                this.lane = '';
            else
                this.lane = p.Results.busStopOrLane;
                this.busStop = {};
            end
			
			this.endPos = p.Results.endPos;
			this.startPos = p.Results.startPos;
			this.friendlyPos = p.Results.friendlyPos;
			this.duration = p.Results.duration;
			this.until = p.Results.until;
			this.index = p.Results.index;
			this.triggered = p.Results.triggered;
            this.containerTriggered = p.Results.containerTriggered;
            this.parking = p.Results.parking;
            this.actType = p.Results.actType;
        end
    end
end