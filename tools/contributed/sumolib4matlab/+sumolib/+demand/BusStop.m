classdef BusStop
	properties %(GetAccess = private)
		id
        lane
        startPos
        endPos
        friendlyPos
		line
    end
    methods
		function this = BusStop(varargin)
            %sumolib.demand.BusStop constructs a BusStop object.
            %   myBusStop = sumolib.demand.BusStop(ID,LANE) creates an
            %   object myBusStop identified by the string ID and placed in
            %   the lane with ID LANE.
            %   Further properties can be defined as name-value pairs.
            %   These properties can be found at:
            %   http://sumo.dlr.de/wiki/Simulation/Public_Transport
            
            %   Copyright 2015 Universidad Nacional de Colombia,
            %   Politecnico Jaime Isaza Cadavid.
            %   $Id$
            
			p = inputParser;
			p.FunctionName = 'sumolib.demand.busStop';
			p.addRequired('id', @ischar);
			p.addRequired('lane', @ischar);
			p.addOptional('startPos', [], @isnumeric);
			p.addOptional('endPos', [], @isnumeric);
            p.addOptional('friendlyPos', [], @isbool);
            p.addOptional('line', '', @ischar);
			p.parse(varargin{:})
			
			this.id = p.Results.id;
			this.lane = p.Results.lane;
			this.startPos = p.Results.startPos;
			this.endPos = p.Results.endPos;
			this.friendlyPos = p.Results.friendlyPos;
			this.line = p.Results.line;
			
        end
    end
end