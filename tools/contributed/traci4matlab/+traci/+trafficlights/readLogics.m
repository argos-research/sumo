function logics = readLogics(result)
%readLogics Internal function to read the complete program of the traffic lights.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: readLogics.m 31 2016-09-28 15:16:56Z afacostag $

result.readLength();
nbLogics = result.readInt();    % Number of logics
logics = cell(1,nbLogics);
for i=1:nbLogics
    result.read(1);                       % Type of SubID
    subID = result.readString();
    result.read(1);                       % Type of Type
    type = result.readInt();                % Type
    result.read(1);                       % Type of SubParameter
    subParameter = result.readInt();        % SubParameter
    result.read(1);                       % Type of Current phase index
    currentPhaseIndex = result.readInt();   % Current phase index
    result.read(1);                       % Type of Number of phases
    nbPhases = result.readInt();            % Number of phases
    phases = cell(1,nbPhases);
    for j=1:nbPhases
        result.read(1);                   % Type of Duration
        duration = result.readInt();        % Duration
        result.read(1);                   % Type of Duration1
        duration1 = result.readInt();       % Duration1
        result.read(1);                   % Type of Duration2
        duration2 = result.readInt();       % Duration2
        result.read(1);                   % Type of Phase Definition
        phaseDef = result.readString();      % Phase Definition
        phase = traci.trafficlights.Phase(duration, duration1, duration2, phaseDef);
        phases{j} = phase;
    end
    logic = traci.trafficlights.Logic(subID, type, subParameter, currentPhaseIndex, phases);
    logics{i} = logic;
end