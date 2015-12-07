classdef Flow
	properties %(GetAccess = private)
%         beginTime
%         endTime
        type
		from
		to
        via
		demandType
        demand
        number
		color
		departLane
		departPos
		departSpeed
		arrivalLane
		arrivalPos
		arrivalSpeed
        line
        personNumber
        containerNumber
        stop
    end
	methods
		function this = Flow(varargin)
            %sumolib.demand.Flow constructs a Flow object.
            %   myFlow = sumolib.demand.Flow(FROM) creates an object
            %   myFlow representing a flow of vehicles with an origin given
            %   by the FROM string, which is the ID of the origin edge. The
            %   related number of vehicles is generated when the
            %   generateDemand function is executed specifying this flow,
            %   using the SUMO defaults.
            %   myFlow = sumolib.demand.Flow(...,DEMANTYPE,DEMAND)
            %   Specifies the type of demand as a string and the demand as
            %   a numeric array. The available demand types are
            %   vehsPerHour, period and probability with corresponding
            %   demands given in vehicles per hour, seconds and a
            %   probability between 0 and 1. Note that you must define both
            %   DEMANDTYPE and DEMAND.
            %   Further properties can be defined as name-value pairs.
            %   These properties can be found at:
            %   http://sumo.dlr.de/wiki/Demand/Shortest_or_Optimal_Path_Routing#Flow_Definitions
            
            %   Copyright 2015 Universidad Nacional de Colombia,
            %   Politecnico Jaime Isaza Cadavid.
            %   $Id$
            
            demandTypes = {'vehsPerHour', 'period', 'probability'};
            departLaneTypes = {'random','free','departLane'};
            departPosTypes = {'random','free','random_free','base'};
            departSpeedTypes = {'random','max'};
            arrivalLaneTypes = {'current'};
            arrivalPosTypes = {'random','max'};
            arrivalSpeedTypes = {'current'};
            
            p = inputParser;
            p.FunctionName = 'sumolib.demand.Flow';
            p.addRequired('from', @ischar);
            % 	p.addRequired('beginTime', @isnumeric);
            % 	p.addOptional('endTime', [], @isnumeric)
            p.addOptional('demandType', [], @(x) any(validatestring(x,demandTypes)))
            p.addOptional('demand', [], @isnumeric)
            p.addOptional('type', [], @(x) isa(x,'sumolib.demand.VehicleType'))
            p.addOptional('to', '', @ischar)
            p.addOptional('via', [], @ischar)
            p.addOptional('number', [], @isnumeric)
            p.addOptional('color', [], @(x) length(x)<=4 && all(x)>=0 && all(x)<=255)
            p.addOptional('departLane', [], @(x) isnumeric(x) || any(validatestring(x,departLaneTypes)))
            p.addOptional('departPos', [], @(x) isnumeric(x) && x>=0 || any(validatestring(x,departPosTypes)))
            p.addOptional('departSpeed', [], @(x) isnumeric(x) && x>=0 || any(validatestring(x,departSpeedTypes)))
            p.addOptional('arrivalLane', [], @(x) isnumeric(x) || any(validatestring(x,arrivalLaneTypes)))
            p.addOptional('arrivalPos', [], @(x) isnumeric(x) && x>=0 || any(validatestring(x,arrivalPosTypes)))
            p.addOptional('arrivalSpeed', [], @(x) isnumeric(x) && x>=0 || any(validatestring(x,arrivalSpeedTypes)))
            p.addOptional('stop', {}, @iscell)
            p.parse(varargin{:})
            
            if xor(isempty(p.Results.demandType),isempty(p.Results.demand))
                throw(MException('Flow:demandError',...
                    'Demand type or demand is not specified'));
            end
            
            if isempty(p.Results.demand) && isempty(p.Results.number)
                throw(MException('Flow:demandError',...
                    'You have to provide demand type or vehicle number'));
            end
            
            this.from = p.Results.from;
%             this.beginTime = p.Results.beginTime;
% 			this.endTime = p.Results.endTime;
            this.type = p.Results.type;
			this.from = p.Results.from;
			this.to = p.Results.to;
            this.via = p.Results.via;
            this.demandType = p.Results.demandType;
			this.demand = p.Results.demand;
			this.number = p.Results.number;
			this.color = num2str(p.Results.color);
            this.color = strrep(this.color, '  ', ',');
			this.departLane = p.Results.departLane;
			this.departPos = p.Results.departPos;
			this.departSpeed = p.Results.departSpeed;
			this.arrivalLane = p.Results.arrivalLane;
			this.arrivalPos = p.Results.arrivalPos;
			this.arrivalSpeed = p.Results.arrivalSpeed;
            this.stop = p.Results.stop;
			
		end
	end
end