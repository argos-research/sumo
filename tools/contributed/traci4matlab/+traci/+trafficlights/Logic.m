classdef Logic
%Logic constructs a Logic object.
%   myLogic = Logic(SUBID,TYPE,SUBPARAMETER,CURRENTPHASEINDEX,PHASES)
%   constructs a Logic object myLogic containing an ID SUBID, astarting 
%   phase index CURRENTPHASEINDEX, and a cell array of traci.Phase objects,
%   PHASES, which represent the phase definitions of trafic lights' program
%   myLogic. TYPE and SUBPARAMETER are not currently implemented in SUMO 
%   server, therefore, the must be set to zero.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: Logic.m 31 2016-09-28 15:16:56Z afacostag $

    properties
        subID
        type
        subParameter
        currentPhaseIndex
        phases
    end
    methods
        function this = Logic(subID, type, subParameter, currentPhaseIndex, phases)            
            this.subID = subID;
            this.type = type;
            this.subParameter = subParameter;
            this.currentPhaseIndex = currentPhaseIndex;
            this.phases = phases;
        end
        
        function display(this)
            disp('Logic:');
            disp(['subID: ' num2str(this.subID)]);
            disp(['type: ' num2str(this.type)]);
            disp(['subParameter: ' num2str(this.subParameter)]);
            disp(['currentPhaseIndex: ' num2str(this.currentPhaseIndex)]);
            for i=1:length(this.phases)
                display(this.phases{i});
            end
        end
        
    end
end