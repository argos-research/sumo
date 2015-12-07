classdef TurnProbability
	properties %(GetAccess = private)
%         beginTime
%         endTime
        fromEdge
        toEdges
        probabilities
    end
	methods
		function this = TurnProbability(varargin)
            %sumolib.demand.TurnProbability constructs a TurnProbability
            %object.
            %   myTurnProbability = sumolib.demand.TurnProbability(FROMEDGE,...
            %   TOEDGES,PROBABILITIES) creates an object myTurnProbability
            %   where FROMEDGE is the ID of the SUMO edge of interest,
            %   TOEDGES is a cell array containing the IDs of edges
            %   connected to FROMEDGE and PROBABILITIES is an array of the
            %   same length than TOEDGES that specifies the turning
            %   probabilities with an origin given by FROMEDGE and
            %   destinations given by FROMEDGES.
            
            %   Copyright 2015 Universidad Nacional de Colombia,
            %   Politecnico Jaime Isaza Cadavid.
            %   $Id$
            
			p = inputParser;
			p.FunctionName = 'sumolib.demand.TurnProbability';
% 			p.addRequired('beginTime', @isnumeric);
% 			p.addRequired('endTime', @isnumeric);
            p.addRequired('fromEdge', @ischar);
            p.addRequired('toEdges', @iscell);
            p.addRequired('probabilities', @iscell);
            
			p.parse(varargin{:})
			
% 			this.beginTime = p.Results.beginTime;
%             this.endTime = p.Results.endTime;
			this.fromEdge = p.Results.fromEdge;
            this.toEdges = p.Results.toEdges;
			this.probabilities = p.Results.probabilities;
			
		end
	end
end